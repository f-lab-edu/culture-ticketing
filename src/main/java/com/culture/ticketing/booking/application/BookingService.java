package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import com.culture.ticketing.booking.exception.AlreadyBookingShowSeatsExistsException;
import com.culture.ticketing.booking.exception.BookingTotalPriceNotMatchException;
import com.culture.ticketing.booking.infra.BookingRepository;
import com.culture.ticketing.show.round_performer.application.RoundService;
import com.culture.ticketing.show.round_performer.exception.RoundNotFoundException;
import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.exception.UserNotFoundException;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RoundService roundService;
    private final BookingShowSeatService bookingShowSeatService;

    public BookingService(BookingRepository bookingRepository, UserService userService, RoundService roundService, BookingShowSeatService bookingShowSeatService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roundService = roundService;
        this.bookingShowSeatService = bookingShowSeatService;
    }

    @Transactional
    public void createBooking(BookingSaveRequest request) {

        checkValidBookingSaveRequest(request);

        if (userService.notExistsById(request.getUserId())) {
            throw new UserNotFoundException(request.getUserId());
        }

        if (roundService.notExistsById(request.getRoundId())) {
            throw new RoundNotFoundException(request.getRoundId());
        }

        int priceSum = bookingShowSeatService.getTotalPriceByShowIdAndShowSeatIds(request.getShowSeatIds());
        if (request.getTotalPrice() != priceSum) {
            throw new BookingTotalPriceNotMatchException();
        }

        if (bookingShowSeatService.hasAlreadyBookingShowSeatsByRoundId(request.getRoundId(), request.getShowSeatIds())) {
            throw new AlreadyBookingShowSeatsExistsException();
        }

        bookingRepository.save(request.toEntity());
    }

    private void checkValidBookingSaveRequest(BookingSaveRequest request) {

        Objects.requireNonNull(request.getUserId(), "유저 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundId(), "회차 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getTotalPrice() >= 0, "총 가격은 0 이상 숫자로 입력해주세요.");
        Preconditions.checkArgument(request.getShowSeatIds() != null && request.getShowSeatIds().size() != 0, "예약 좌석 정보를 입력해주세요.");
    }
}