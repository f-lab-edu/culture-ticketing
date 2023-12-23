package com.culture.ticketing.show.application;

import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest;
import com.culture.ticketing.show.domain.ShowFloor;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowFloorRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_FLOOR_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SHOW_FLOOR_COUNT;

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

        Objects.requireNonNull(request.getShowSeatGradeId(), EMPTY_SHOW_SEAT_GRADE_ID.getMessage(request.getShowSeatGradeId()));
        Preconditions.checkArgument(StringUtils.hasText(request.getShowFloorName()), EMPTY_SHOW_FLOOR_NAME.getMessage());
        Preconditions.checkArgument(request.getCount() > 0, NEGATIVE_SHOW_FLOOR_COUNT.getMessage());

        if (!showSeatGradeService.existsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        ShowFloor showFloor = request.toEntity();
        showFloorRepository.save(showFloor);
    }
}
