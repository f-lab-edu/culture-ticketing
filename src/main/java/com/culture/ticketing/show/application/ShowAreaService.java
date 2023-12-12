package com.culture.ticketing.show.application;

import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.exception.AreaNotFoundException;
import com.culture.ticketing.place.infra.AreaRepository;
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.domain.ShowArea;
import com.culture.ticketing.show.infra.ShowAreaRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_AREA_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_AREA_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;

@Service
public class ShowAreaService {

    private final ShowAreaRepository showAreaRepository;
    private final AreaRepository areaRepository;
    private final ShowService showService;

    public ShowAreaService(ShowAreaRepository showAreaRepository, AreaRepository areaRepository, ShowService showService) {
        this.showAreaRepository = showAreaRepository;
        this.areaRepository = areaRepository;
        this.showService = showService;
    }

    public void createShowArea(ShowAreaSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getShowAreaName()), EMPTY_SHOW_AREA_NAME.getMessage());
        Preconditions.checkArgument(request.getAreaIds() != null && request.getAreaIds().size() > 0, EMPTY_AREA_ID.getMessage());

        Show show = showService.findShowById(request.getShowId());
        List<Area> foundAreas = areaRepository.findByAreaIdInAndPlaceId(request.getAreaIds(), show.getPlaceId());
        if (request.getAreaIds().size() != foundAreas.size()) {
            String notMatchingAreaIds = request.getAreaIds().stream()
                    .filter(areaId -> foundAreas.stream().noneMatch(area -> area.getAreaId().equals(areaId)))
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));

            throw new AreaNotFoundException(notMatchingAreaIds);
        }

        List<ShowArea> showAreas = request.toEntities();
        showAreaRepository.saveAll(showAreas);
    }
}
