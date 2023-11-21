package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest;
import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.place.exception.DuplicatedPlaceSeatException;
import com.culture.ticketing.place.infra.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final PlaceService placeService;

    public SeatService(SeatRepository seatRepository, PlaceService placeService) {
        this.seatRepository = seatRepository;
        this.placeService = placeService;
    }

    @Transactional
    public void createPlaceSeat(PlaceSeatSaveRequest request) {

        if (!StringUtils.hasText(request.getArea())) {
            throw new BaseException(BaseResponseStatus.EMPTY_SEAT_AREA);
        }
        if (request.getSeatRow() <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_ROW);
        }
        if (request.getSeatNumber() <= 0) {
            throw new BaseException(BaseResponseStatus.NEGATIVE_SEAT_NUMBER);
        }
        if (request.getPlaceId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ID);
        }

        placeService.getPlaceByPlaceId(request.getPlaceId());
        Seat seat = request.toEntity();
        checkDuplicatedSeat(seat);
        seatRepository.save(seat);
    }

    private void checkDuplicatedSeat(Seat seat) {
        seatRepository.findByPlaceIdAndCoordinateXAndCoordinateY(seat.getPlaceId(), seat.getCoordinateX(), seat.getCoordinateY())
                .ifPresent(s -> {
                    throw new DuplicatedPlaceSeatException();
                });
        seatRepository.findByPlaceIdAndAreaAndSeatRowAndSeatNumber(seat.getPlaceId(), seat.getArea(), seat.getSeatRow(), seat.getSeatNumber())
                .ifPresent(s -> {
                    throw new DuplicatedPlaceSeatException();
                });
    }
}
