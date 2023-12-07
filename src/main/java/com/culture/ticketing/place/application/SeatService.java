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

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SEAT_NUMBER;
import static com.culture.ticketing.common.response.BaseResponseStatus.NEGATIVE_SEAT_ROW;

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

        Objects.requireNonNull(request.getAreaId(), EMPTY_PLACE_ID.getMessage());
        Preconditions.checkArgument(request.getSeatRow() <= 0, NEGATIVE_SEAT_ROW.getMessage());
        Preconditions.checkArgument(request.getSeatNumber() <= 0, NEGATIVE_SEAT_NUMBER.getMessage());

        if (!areaService.existsById(request.getAreaId())) {
            throw new AreaNotFoundException(request.getAreaId());
        }

        Seat seat = request.toEntity();
        checkDuplicatedSeat(seat);
        seatRepository.save(seat);
    }

    private void checkDuplicatedSeat(Seat seat) {
        seatRepository.findByAreaIdAndCoordinateXAndCoordinateY(seat.getAreaId(), seat.getCoordinateX(), seat.getCoordinateY())
                .ifPresent(s -> {
                    throw new DuplicatedPlaceSeatException();
                });
        seatRepository.findByAreaIdAndSeatRowAndSeatNumber(seat.getAreaId(), seat.getSeatRow(), seat.getSeatNumber())
                .ifPresent(s -> {
                    throw new DuplicatedPlaceSeatException();
                });
    }

    @Transactional(readOnly = true)
    public Seat getSeatBySeatId(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(SeatNotFoundException::new);
    }
}
