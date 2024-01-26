package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.infra.BookingRepository;
import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.exception.RoundNotFoundException;
import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;
    private final RoundService roundService;

    public BookingService(BookingRepository bookingRepository, UserService userService, RoundService roundService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roundService = roundService;
    }

    @Transactional
    public void createBooking(Booking booking) {

        if (userService.notExistsById(booking.getUserId())) {
            throw new UserNotFoundException(booking.getUserId());
        }

        if (roundService.notExistsById(booking.getRoundId())) {
            throw new RoundNotFoundException(booking.getRoundId());
        }

        bookingRepository.save(booking);
    }

}