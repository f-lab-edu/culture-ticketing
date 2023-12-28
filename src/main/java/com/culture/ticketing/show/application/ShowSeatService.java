package com.culture.ticketing.show.application;

import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.place.infra.SeatRepository;
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

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatGradeService showSeatGradeService;
    private final SeatRepository seatRepository;

    public ShowSeatService(ShowSeatRepository showSeatRepository, ShowSeatGradeService showSeatGradeService, SeatRepository seatRepository) {
        this.showSeatRepository = showSeatRepository;
        this.showSeatGradeService = showSeatGradeService;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public void createShowSeat(ShowSeatSaveRequest request) {

        Objects.requireNonNull(request.getShowSeatGradeId(), "공연 좌석 등급 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getSeatIds(), "좌석 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getSeatIds().size() != 0, "좌석 아이디를 입력해주세요.");

        if (!showSeatGradeService.existsById(request.getShowSeatGradeId())) {
            throw new ShowSeatGradeNotFoundException(request.getShowSeatGradeId());
        }

        List<Seat> foundSeats = seatRepository.findBySeatIdIn(request.getSeatIds());
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
