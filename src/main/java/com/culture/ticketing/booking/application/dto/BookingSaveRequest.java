package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BookingSaveRequest {

    private Long userId;
    private Long roundId;
    private int totalPrice;
    private Set<Long> showSeatIds = new HashSet<>();
    private Set<BookingShowFloorSaveRequest> showFloors = new HashSet<>();

    @Builder
    public BookingSaveRequest(Long userId, Long roundId, int totalPrice, Set<Long> showSeatIds, Set<BookingShowFloorSaveRequest> showFloors) {
        this.userId = userId;
        this.roundId = roundId;
        this.totalPrice = totalPrice;
        this.showSeatIds = showSeatIds;
        this.showFloors = showFloors;
    }

    public List<Long> getShowFloorIds() {
        return showFloors.stream()
                .map(BookingShowFloorSaveRequest::getShowFloorId)
                .collect(Collectors.toList());
    }

    public Booking toEntity() {
        Booking booking = Booking.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(totalPrice)
                .bookingStatus(BookingStatus.SUCCESS)
                .build();

        Set<BookingShowSeat> bookingShowSeats = showSeatIds.stream()
                .map(showSeatId -> BookingShowSeat.builder()
                        .showSeatId(showSeatId)
                        .booking(booking)
                        .build())
                .collect(Collectors.toSet());
        booking.setBookingShowSeats(bookingShowSeats);

        Set<BookingShowFloor> bookingShowFloors = showFloors.stream()
                .map(showFloor -> BookingShowFloor.builder()
                        .showFloorId(showFloor.getShowFloorId())
                        .entryOrder(showFloor.getEntryOrder())
                        .booking(booking)
                        .build())
                .collect(Collectors.toSet());
        booking.setBookingShowFloors(bookingShowFloors);

        return booking;
    }

}
