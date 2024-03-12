package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "예약 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class BookingSaveRequest {

    @Schema(description = "유저 아이디")
    private Long userId;
    @Schema(description = "회차 아이디")
    private Long roundId;
    @Schema(description = "총 가격")
    private int totalPrice;
    @Schema(description = "예약 공연 좌석 아이디 목록")
    private Set<Long> showSeatIds = new HashSet<>();

    @Builder
    public BookingSaveRequest(Long userId, Long roundId, int totalPrice, Set<Long> showSeatIds) {
        this.userId = userId;
        this.roundId = roundId;
        this.totalPrice = totalPrice;
        this.showSeatIds = showSeatIds;
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

        return booking;
    }

}
