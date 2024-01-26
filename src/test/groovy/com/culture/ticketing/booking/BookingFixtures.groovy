package com.culture.ticketing.booking

import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingStatus;

class BookingFixtures {

    static Booking createBooking(Long bookingId, Long userId = 1L, Long roundId = 1L, int totalPrice = 100000, BookingStatus bookingStatus = BookingStatus.SUCCESS) {
        return Booking.builder()
                .bookingId(bookingId)
                .userId(userId)
                .roundId(roundId)
                .totalPrice(totalPrice)
                .bookingStatus(bookingStatus)
                .build();
    }

}
