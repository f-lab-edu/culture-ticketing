package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatGradeService showSeatGradeService;
    private final SeatService seatService;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowSeatGradeService showSeatGradeService, SeatService seatService) {
        this.showSeatRepository = showSeatRepository;
        this.showSeatGradeService = showSeatGradeService;
        this.seatService = seatService;
    }

    @Transactional
    public void createShowSeat(ShowSeatSaveRequest request) {

        if (request.getShowSeatGradeId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID);
        }
        if (request.getSeatId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SEAT_ID);
        }

        showSeatGradeService.getShowSeatGradeByShowSeatGradeId(request.getShowSeatGradeId());
        seatService.getSeatBySeatId(request.getSeatId());
        ShowSeat showSeat = request.toEntity();
        showSeatRepository.save(showSeat);
    }
}
