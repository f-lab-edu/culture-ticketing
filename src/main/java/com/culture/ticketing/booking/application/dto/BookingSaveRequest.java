package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.domain.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookingSaveRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long roundId;
    @PositiveOrZero
    private int totalPrice;
    @NotNull
    private List<Long> showSeatIds;
    @NotNull
    private List<BookingShowFloorSaveRequest> showFloors;

    @Builder
    public BookingSaveRequest(Long userId, Long roundId, int totalPrice, List<Long> showSeatIds, List<BookingShowFloorSaveRequest> showFloors) {
        this.userId = userId;
        this.roundId = roundId;
        this.totalPrice = totalPrice;
        this.showSeatIds = showSeatIds;
        this.showFloors = showFloors;
    }

    public Booking toEntity() {
        return Booking.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(totalPrice)
                .bookingStatus(BookingStatus.SUCCESS)
                .build();
    }

}
