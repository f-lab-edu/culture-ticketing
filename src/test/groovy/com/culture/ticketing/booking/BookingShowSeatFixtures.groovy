package com.culture.ticketing.booking

import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingShowSeat

class BookingShowSeatFixtures {

    static createBookingShowSeat(Map map = [:]) {
        BookingShowSeat bookingShowSeat = BookingShowSeat.builder()
                .bookingShowSeatId(map.getOrDefault("bookingShowSeatId", null) as Long)
                .showSeatId(map.getOrDefault("showSeatId", 1L) as Long)
                .booking(map.getOrDefault("booking", BookingFixtures.createBooking()) as Booking)
                .build();
        bookingShowSeat.booking.bookingShowSeats.add(bookingShowSeat);
        return bookingShowSeat;
    }
}
