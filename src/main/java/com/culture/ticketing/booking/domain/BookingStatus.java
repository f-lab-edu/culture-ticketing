package com.culture.ticketing.booking.domain;

public enum BookingStatus {

    SUCCESS("예약완료"),
    CANCELLED("예약취소");

    private final String statusName;

    BookingStatus(String statusName) {
        this.statusName = statusName;
    }
}
