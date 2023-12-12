package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.SeatService;
import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException;
import com.culture.ticketing.show.infra.ShowSeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Objects.requireNonNull(request.getSeatIds(), EMPTY_SEAT_ID.getMessage());
        Preconditions.checkArgument(request.getSeatIds().size() != 0, EMPTY_SEAT_ID.getMessage());

        if (showSeatGradeService.existsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }
        List<Seat> foundSeats = seatService.findSeatsByIds(request.getSeatIds());
        if (request.getSeatIds().size() != foundSeats.size()) {
            String notMatchingSeatIds = request.getSeatIds().stream()
                    .filter(seatId -> foundSeats.stream().noneMatch(seat -> seat.getSeatId().equals(seatId)))
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));
            throw new SeatNotFoundException(notMatchingSeatIds);
        }

        List<ShowSeat> showSeats = request.toEntities();
        showSeatRepository.saveAll(showSeats);
    }
}
