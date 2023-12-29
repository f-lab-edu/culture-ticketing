package com.culture.ticketing.place.exception;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException(String seatId) {
        super(String.format("존재하지 않는 좌석입니다. (seatIds = %s)", seatId));
    }
}
