package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.exception.BookingTotalPriceNotMatchException;
import com.culture.ticketing.booking.infra.BookingRepository;
import com.culture.ticketing.show.application.RoundService;
import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.exception.RoundNotFoundException;
import com.culture.ticketing.user.application.UserService;
import com.culture.ticketing.user.exception.UserNotFoundException;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RoundService roundService;
    private final ShowSeatService showSeatService;
    private final ShowFloorService showFloorService;

    public BookingService(BookingRepository bookingRepository, UserService userService, RoundService roundService, ShowSeatService showSeatService, ShowFloorService showFloorService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.roundService = roundService;
        this.showSeatService = showSeatService;
        this.showFloorService = showFloorService;
    }

    @Transactional
    public void createBooking(BookingSaveRequest request) {

        Objects.requireNonNull(request.getUserId(), "유저 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundId(), "회차 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getTotalPrice() >= 0, "총 가격은 0 이상 숫자로 입력해주세요.");
        Preconditions.checkArgument(request.getShowSeatIds() != null && request.getShowFloors() != null &&
                (request.getShowSeatIds().size() != 0 || request.getShowFloors().size() != 0), "예약 좌석 정보를 입력해주세요.");

        if (userService.notExistsById(request.getUserId())) {
            throw new UserNotFoundException(request.getUserId());
        }

        if (roundService.notExistsById(request.getRoundId())) {
            throw new RoundNotFoundException(request.getRoundId());
        }

        int priceSum = showSeatService.getTotalPriceByShowSeatIds(request.getShowSeatIds())
                + showFloorService.getTotalPriceByShowFloorIds(request.getShowFloors().stream()
                .map(BookingShowFloorSaveRequest::getShowFloorId)
                .collect(Collectors.toList()));

        if (request.getTotalPrice() != priceSum) {
            throw new BookingTotalPriceNotMatchException();
        }

        bookingRepository.save(request.toEntity());
    }

}