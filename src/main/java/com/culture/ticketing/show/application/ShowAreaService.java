package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowAreaGradeMapById;
import com.culture.ticketing.show.application.dto.ShowAreaResponse;
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest;
import com.culture.ticketing.show.exception.ShowAreaGradeNotFoundException;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowAreaRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ShowAreaService {

    private final ShowAreaRepository showAreaRepository;
    private final ShowService showService;
    private final ShowAreaGradeService showAreaGradeService;

    public ShowAreaService(ShowAreaRepository showAreaRepository, ShowService showService, ShowAreaGradeService showAreaGradeService) {
        this.showAreaRepository = showAreaRepository;
        this.showService = showService;
        this.showAreaGradeService = showAreaGradeService;
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long showAreaId) {
        return !showAreaRepository.existsById(showAreaId);
    }

    @Transactional
    public void createShowArea(ShowAreaSaveRequest request) {

        checkValidShowAreaSaveRequest(request);

        if (showService.notExistsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        if (showAreaGradeService.notExistsById(request.getShowAreaGradeId())) {
            throw new ShowAreaGradeNotFoundException(request.getShowAreaGradeId());
        }

        showAreaRepository.save(request.toEntity());
    }

    private void checkValidShowAreaSaveRequest(ShowAreaSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getShowAreaGradeId(), "공연 구역 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowAreaName()), "공연 구역명을 입력해주세요.");
    }


    @Transactional(readOnly = true)
    public List<ShowAreaResponse> findShowAreasByShowId(Long showId) {

        ShowAreaGradeMapById showAreaGradeMapById = showAreaGradeService.findShowAreaGradeMapById(showId);

        return showAreaRepository.findByShowId(showId).stream()
                .map(showArea -> new ShowAreaResponse(showArea, showAreaGradeMapById.getById(showArea.getShowAreaGradeId())))
                .collect(Collectors.toList());
    }
}
