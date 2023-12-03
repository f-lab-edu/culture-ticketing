package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.*;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final PlaceService placeService;

    public ShowService(ShowRepository showRepository, PlaceService placeService) {
        this.showRepository = showRepository;
        this.placeService = placeService;
    }

    @Transactional
    public void createShow(ShowSaveRequest request) {

        Objects.requireNonNull(request.getCategory(), EMPTY_SHOW_CATEGORY.getMessage());
        Objects.requireNonNull(request.getAgeRestriction(), EMPTY_SHOW_AGE_RESTRICTION.getMessage());
        Objects.requireNonNull(request.getPlaceId(), EMPTY_SHOW_PLACE_ID.getMessage());
        Objects.requireNonNull(request.getShowStartDate(), EMPTY_SHOW_START_DATE.getMessage());
        Objects.requireNonNull(request.getShowEndDate(), EMPTY_SHOW_END_DATE.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getShowName()), EMPTY_SHOW_NAME.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getPosterImgUrl()), EMPTY_SHOW_POSTER_IMG_URL.getMessage());
        Preconditions.checkArgument(request.getRunningTime() > 0, NOT_POSITIVE_SHOW_RUNNING_TIME.getMessage());

        if (!placeService.existsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        Show show = request.toEntity();
        showRepository.save(show);
    }

    @Transactional(readOnly = true)
    public Show findShowById(Long showId) {
        return showRepository.findById(showId).orElseThrow(() -> {
            throw new ShowNotFoundException(showId);
        });
    }
}
