package com.culture.ticketing.booking.exception;

public class BookingTotalPriceNotMatchException extends RuntimeException {

    public BookingTotalPriceNotMatchException() {
        super("입력된 예약의 총 금액이 알맞지 않습니다.");
    }
}
