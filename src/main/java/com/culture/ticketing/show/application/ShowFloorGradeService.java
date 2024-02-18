package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowFloorGradeSaveRequest;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.ShowFloorGradeRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class ShowFloorGradeService {

    private final ShowFloorGradeRepository showFloorGradeRepository;
    private final ShowService showService;

    public ShowFloorGradeService(ShowFloorGradeRepository showFloorGradeRepository, ShowService showService) {
        this.showFloorGradeRepository = showFloorGradeRepository;
        this.showService = showService;
    }

    @Transactional
    public void createShowFloorGrade(ShowFloorGradeSaveRequest request) {

        checkValidShowFloorGradeSaveRequest(request);

        if (showService.notExistsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        showFloorGradeRepository.save(request.toEntity());
    }

    private void checkValidShowFloorGradeSaveRequest(ShowFloorGradeSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowFloorGradeName()), "공연 플로어 등급명을 입력해주세요.");
        Preconditions.checkArgument(request.getPrice() >= 0, "공연 좌석 가격을 0 이상으로 입력해주세요.");
    }
}
