package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest;
import com.culture.ticketing.show.domain.ShowFloor;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowFloorRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class ShowFloorService {

    private final ShowFloorRepository showFloorRepository;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowFloorService(ShowFloorRepository showFloorRepository, ShowSeatGradeService showSeatGradeService) {
        this.showFloorRepository = showFloorRepository;
        this.showSeatGradeService = showSeatGradeService;
    }

    @Transactional
    public void createShowFloor(ShowFloorSaveRequest request) {

        Objects.requireNonNull(request.getShowSeatGradeId(), "공연 좌석 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getShowFloorName()), "공연 플로어 구역명을 입력해주세요.");
        Preconditions.checkArgument(request.getCount() > 0, "공연 플로어 인원수를 1 이상 숫자로 입력해주세요.");

        if (showSeatGradeService.notExistsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        ShowFloor showFloor = request.toEntity();
        showFloorRepository.save(showFloor);
    }
}
