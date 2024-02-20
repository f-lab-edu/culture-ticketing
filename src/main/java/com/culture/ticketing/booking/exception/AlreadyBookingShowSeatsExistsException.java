package com.culture.ticketing.booking.exception;

public class AlreadyBookingShowSeatsExistsException extends RuntimeException {

    public AlreadyBookingShowSeatsExistsException() {
        super("이미 예약된 좌석입니다.");
    }
}
