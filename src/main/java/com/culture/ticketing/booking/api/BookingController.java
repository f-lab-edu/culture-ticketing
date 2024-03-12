package com.culture.ticketing.booking.api;

import com.culture.ticketing.booking.application.BookingService;
import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"예약 Controller"})
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiOperation(value = "예약 생성 API")
    @PostMapping
    public void createBooking(@RequestBody BookingSaveRequest request) {

        bookingService.createBooking(request);
    }
}
