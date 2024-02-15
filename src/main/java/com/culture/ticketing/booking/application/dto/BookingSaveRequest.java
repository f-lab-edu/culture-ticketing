package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BookingSaveRequest {

    private Long userId;
    private Long roundId;
    private int totalPrice;
    private List<Long> showSeatIds = new ArrayList<>();
    private List<BookingShowFloorSaveRequest> showFloors = new ArrayList<>();

    @Builder
    public BookingSaveRequest(Long userId, Long roundId, int totalPrice, List<Long> showSeatIds, List<BookingShowFloorSaveRequest> showFloors) {
        this.userId = userId;
        this.roundId = roundId;
        this.totalPrice = totalPrice;
        this.showSeatIds = showSeatIds;
        this.showFloors = showFloors;
    }

    public Booking toEntity() {
        Booking booking = Booking.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(totalPrice)
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        List<BookingShowSeat> bookingShowSeats = showSeatIds.stream()
                .map(showSeatId -> BookingShowSeat.builder()
                        .showSeatId(showSeatId)
                        .booking(booking)
                        .build())
                .collect(Collectors.toList());
        booking.setBookingShowSeats(bookingShowSeats);

        List<BookingShowFloor> bookingShowFloors = showFloors.stream()
                .map(showFloor -> BookingShowFloor.builder()
                        .showFloorId(showFloor.getShowFloorId())
                        .entryOrder(showFloor.getEntryOrder())
                        .booking(booking)
                        .build())
                .collect(Collectors.toList());
        booking.setBookingShowFloors(bookingShowFloors);

        return booking;
    }

}
