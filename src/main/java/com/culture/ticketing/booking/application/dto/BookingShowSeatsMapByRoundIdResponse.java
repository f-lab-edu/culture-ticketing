package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingShowSeatsMapByRoundIdResponse {

    private final Map<Long, List<ShowSeat>> bookingShowSeatsMapByRoundId;

    public BookingShowSeatsMapByRoundIdResponse(List<BookingShowSeat> bookingShowSeats, List<ShowSeat> showSeats) {

        Map<Long, ShowSeat> showSeatMapById = showSeats.stream()
                .collect(Collectors.toMap(ShowSeat::getShowSeatId, Function.identity()));

        this.bookingShowSeatsMapByRoundId = bookingShowSeats.stream()
                .collect(Collectors.groupingBy(
                        bookingShowSeat -> bookingShowSeat.getBooking().getRoundId(),
                        Collectors.mapping(bookingShowSeat -> showSeatMapById.get(bookingShowSeat.getShowSeatId()), Collectors.toList())
                ));
    }

    public Long getBookingShowSeatCountByRoundIdAndShowSeatGradeId(Long roundId, Long showSeatGradeId) {

        Map<Long, Long> bookingShowSeatCntMapByShowSeatGradeId = bookingShowSeatsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(ShowSeat::getShowSeatGradeId, Collectors.counting()));

        return bookingShowSeatCntMapByShowSeatGradeId.getOrDefault(showSeatGradeId, 0L);
    }
}
