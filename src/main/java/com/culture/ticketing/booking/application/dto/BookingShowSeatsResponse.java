package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookingShowSeatsResponse {

    private final List<BookingShowSeat> bookingShowSeats;

    public BookingShowSeatsResponse(List<BookingShowSeat> bookingShowSeats) {
        this.bookingShowSeats = bookingShowSeats;
    }

    public List<Long> getShowSeatIds() {

        return this.bookingShowSeats.stream()
                .map(BookingShowSeat::getShowSeatId)
                .collect(Collectors.toList());
    }

    public List<BookingShowSeat> getBookingShowSeats() {

        return Collections.unmodifiableList(this.bookingShowSeats);
    }

    public boolean isAvailableShowSeat(Long showSeatId) {
        return bookingShowSeats.stream()
                .noneMatch(bookingShowSeat -> bookingShowSeat.getShowSeatId().equals(showSeatId));
    }
}
