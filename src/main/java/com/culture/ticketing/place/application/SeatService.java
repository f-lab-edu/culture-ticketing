package com.culture.ticketing.place.application;

import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.place.exception.AreaNotFoundException;
import com.culture.ticketing.place.exception.DuplicatedPlaceSeatException;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.place.infra.SeatRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final AreaService areaService;

    public SeatService(SeatRepository seatRepository, AreaService areaService) {
        this.seatRepository = seatRepository;
        this.areaService = areaService;
    }

    @Transactional
    public void createPlaceSeat(PlaceSeatSaveRequest request) {

        Objects.requireNonNull(request.getAreaId(), "구역 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getSeatRow() > 0, "좌석 행을 1 이상 숫자로 입력해주세요.");
        Preconditions.checkArgument(request.getSeatNumber() > 0, "좌석 번호를 1 이상 숫자로 입력해주세요.");

        if (areaService.notExistsById(request.getAreaId())) {
            throw new AreaNotFoundException(request.getAreaId());
        }

        Seat seat = request.toEntity();
        checkDuplicatedSeat(seat);
        seatRepository.save(seat);
    }

    private void checkDuplicatedSeat(Seat seat) {
        seatRepository.findByAreaIdAndSeatRowAndSeatNumber(seat.getAreaId(), seat.getSeatRow(), seat.getSeatNumber())
                .ifPresent(s -> {
                    throw new DuplicatedPlaceSeatException();
                });
    }

    @Transactional(readOnly = true)
    public void checkSeatsExists(Set<Long> seatIds) {

        Set<Long> copySeatIds = new HashSet<>(seatIds);
        List<Seat> foundSeats = seatRepository.findBySeatIdIn(copySeatIds);
        if (copySeatIds.size() != foundSeats.size()) {
            for (Seat foundSeat : foundSeats) {
                copySeatIds.remove(foundSeat.getSeatId());
            }
            throw new SeatNotFoundException(copySeatIds.toString());
        }
    }
}
