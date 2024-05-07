package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import lombok.Getter;

@Getter
public class BookingShowSeatResponse {

    private final Long bookingShowSeatId;
    private final Long showSeatId;
    private final Long roundId;


    public BookingShowSeatResponse(BookingShowSeat bookingShowSeat) {

        this.bookingShowSeatId = bookingShowSeat.getBookingShowSeatId();
        this.showSeatId = bookingShowSeat.getShowSeatId();
        this.roundId = bookingShowSeat.getRoundId();
    }
}
