package com.culture.ticketing.booking.api;

import com.culture.ticketing.booking.application.BookingFacadeService;
import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingFacadeService bookingFacadeService;

    public BookingController(BookingFacadeService bookingFacadeService) {
        this.bookingFacadeService = bookingFacadeService;
    }

    @PostMapping
    public void createBooking(@Valid @RequestBody BookingSaveRequest request) {

        bookingFacadeService.createBooking(request);
    }
}
