package com.culture.ticketing.booking

import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingShowFloor

class BookingShowFloorFixtures {

    static createBookingShowFloor(Map map = [:]) {
        BookingShowFloor bookingShowFloor = BookingShowFloor.builder()
                .bookingShowFloorId(map.getOrDefault("bookingShowFloorId", null) as Long)
                .showFloorId(map.getOrDefault("showFloorId", 1L) as Long)
                .entryOrder(map.getOrDefault("entryOrder", 1) as Integer)
                .booking(map.getOrDefault("booking", BookingFixtures.createBooking()) as Booking)
                .build();
        bookingShowFloor.booking.bookingShowFloors.add(bookingShowFloor);
        return bookingShowFloor;
    }
}
