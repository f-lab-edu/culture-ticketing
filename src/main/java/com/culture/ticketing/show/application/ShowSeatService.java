package com.culture.ticketing.show.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SEAT_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SEAT_GRADE_ID;

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

        Objects.requireNonNull(request.getShowSeatGradeId(), EMPTY_SHOW_SEAT_GRADE_ID.getMessage());
        Objects.requireNonNull(request.getSeatId(), EMPTY_SEAT_ID.getMessage());

        if (seatService.existsById(request.getSeatId())) {
            throw new SeatNotFoundException(request.getSeatId());
        }
        if (showSeatGradeService.existsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        ShowSeat showSeat = request.toEntity();
        showSeatRepository.save(showSeat);
    }
}
