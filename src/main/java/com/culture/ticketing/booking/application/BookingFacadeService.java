package com.culture.ticketing.booking.application;

import com.culture.ticketing.booking.application.dto.BookingSaveRequest;
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.exception.BookingTotalPriceNotMatchException;
import com.culture.ticketing.show.application.ShowFloorService;
import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.domain.ShowFloor;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.domain.ShowSeatGrade;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookingFacadeService {

    private final BookingService bookingService;
    private final BookingShowFloorService bookingShowFloorService;
    private final BookingShowSeatService bookingShowSeatService;
    private final ShowSeatService showSeatService;
    private final ShowFloorService showFloorService;
    private final ShowSeatGradeService showSeatGradeService;

    public BookingFacadeService(BookingService bookingService, BookingShowFloorService bookingShowFloorService, BookingShowSeatService bookingShowSeatService,
                                ShowSeatService showSeatService, ShowFloorService showFloorService, ShowSeatGradeService showSeatGradeService) {
        this.bookingService = bookingService;
        this.bookingShowFloorService = bookingShowFloorService;
        this.bookingShowSeatService = bookingShowSeatService;
        this.showSeatService = showSeatService;
        this.showFloorService = showFloorService;
        this.showSeatGradeService = showSeatGradeService;
    }

    @Transactional
    public void createBooking(BookingSaveRequest request) {

        Objects.requireNonNull(request.getUserId(), "유저 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundId(), "회차 아이디를 입력해주세요.");
        Preconditions.checkArgument(request.getTotalPrice() >= 0, "총 가격은 0 이상 숫자로 입력해주세요.");
        Preconditions.checkArgument(request.getShowSeatIds().size() != 0 || request.getShowFloors().size() != 0, "예약 좌석 정보를 입력해주세요.");

        checkBookingTotalPrice(request.getShowSeatIds(), request.getShowFloors(), request.getTotalPrice());

        Booking booking = request.toEntity();
        bookingService.createBooking(booking);
        bookingShowSeatService.createBookingShowSeats(request.getShowSeatIds(), booking.getBookingId());
        bookingShowFloorService.createBookingShowFloors(request.getShowFloors(), booking.getBookingId());
    }

    private void checkBookingTotalPrice(List<Long> showSeatIds, List<BookingShowFloorSaveRequest> bookingShowFloors, int totalPrice) {
        List<ShowSeat> showSeats = showSeatService.findByIds(showSeatIds);
        List<ShowFloor> showFloors = showFloorService.findByIds(bookingShowFloors.stream()
                .map(BookingShowFloorSaveRequest::getShowFloorId)
                .collect(Collectors.toList()));

        List<Long> showSeatGradeIds = showSeats.stream()
                .map(ShowSeat::getShowSeatGradeId)
                .collect(Collectors.toList());
        showSeatGradeIds.addAll(showFloors.stream()
                .map(ShowFloor::getShowSeatGradeId)
                .collect(Collectors.toList()));
        Map<Long, Integer> priceMapByShowSeatGradeId = showSeatGradeService.findByIds(showSeatGradeIds).stream()
                .collect(Collectors.toMap(ShowSeatGrade::getShowSeatGradeId, ShowSeatGrade::getPrice));

        int priceSum = 0;
        priceSum += showSeats.stream()
                .mapToInt(showSeat -> priceMapByShowSeatGradeId.get(showSeat.getShowSeatGradeId()))
                .sum();
        priceSum += showFloors.stream()
                .mapToInt(showFloor -> priceMapByShowSeatGradeId.get(showFloor.getShowSeatGradeId()))
                .sum();

        if (totalPrice != priceSum) {
            throw new BookingTotalPriceNotMatchException();
        }
    }
}
