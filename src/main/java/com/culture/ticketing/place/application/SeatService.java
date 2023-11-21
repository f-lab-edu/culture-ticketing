package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.place.exception.DuplicatedPlaceSeatException;
import com.culture.ticketing.place.exception.SeatNotFoundException;
import com.culture.ticketing.place.infra.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional
    public void createPlaceSeat(PlaceSeatSaveRequest request) {

        if (request.getSeatRow() <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_ROW);
        }
        if (request.getSeatNumber() <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_NUMBER);
        }
        if (request.getAreaId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ID);
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
