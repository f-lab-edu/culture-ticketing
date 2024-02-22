package com.culture.ticketing.booking

import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingStatus;

class BookingFixtures {

    static Booking createBooking(Map map = [:]) {
        return Booking.builder()
                .bookingId(map.getOrDefault("bookingId", null) as Long)
                .userId(map.getOrDefault("userId", 1L) as Long)
                .roundId(map.getOrDefault("roundId", 1L) as Long)
                .totalPrice(map.getOrDefault("totalPrice", 300000) as Integer)
                .bookingStatus(map.getOrDefault("bookingStatus", BookingStatus.SUCCESS) as BookingStatus)
                .build();
    }

}
