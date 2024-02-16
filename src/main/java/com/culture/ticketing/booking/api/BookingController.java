package com.culture.ticketing.booking.api;

import com.culture.ticketing.booking.application.BookingService;
import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public void createBooking(@RequestBody BookingSaveRequest request) {

        bookingService.createBooking(request);
    }
}
