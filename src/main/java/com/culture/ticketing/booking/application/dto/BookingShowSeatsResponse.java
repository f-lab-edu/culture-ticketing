package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;

import java.util.Collections;
import java.util.List;

public class BookingShowSeatsResponse {

    private final List<BookingShowSeat> bookingShowSeats;

    public BookingShowSeatsResponse(List<BookingShowSeat> bookingShowSeats) {
        this.bookingShowSeats = bookingShowSeats;
    }

    public List<BookingShowSeat> getBookingShowSeats() {

        return Collections.unmodifiableList(this.bookingShowSeats);
    }

    public boolean isAvailableShowSeat(Long showSeatId) {
        return bookingShowSeats.stream()
                .noneMatch(bookingShowSeat -> bookingShowSeat.equalsShowSeatId(showSeatId));
    }
}
