package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingShowSeatMapByShowSeatIdResponse {

    private Map<Long, BookingShowSeat> bookingShowSeatMapByShowSeatId;

    public BookingShowSeatMapByShowSeatIdResponse(List<BookingShowSeat> bookingShowSeats) {
        this.bookingShowSeatMapByShowSeatId = bookingShowSeats.stream()
                .collect(Collectors.toMap(BookingShowSeat::getShowSeatId, Function.identity()));
    }

    public boolean notExistsByShowSeatId(Long showSeatId) {
        return !bookingShowSeatMapByShowSeatId.containsKey(showSeatId);
    }
}
