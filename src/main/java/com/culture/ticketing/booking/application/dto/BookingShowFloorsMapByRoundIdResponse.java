package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.show.show_floor.domain.ShowFloor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingShowFloorsMapByRoundIdResponse {

    private final Map<Long, List<ShowFloor>> bookingShowFloorsMapByRoundId;

    public BookingShowFloorsMapByRoundIdResponse(List<BookingShowFloor> bookingShowFloors, List<ShowFloor> showFloors) {

        Map<Long, ShowFloor> showFloorMapById = showFloors.stream()
                .collect(Collectors.toMap(ShowFloor::getShowFloorId, Function.identity()));

        this.bookingShowFloorsMapByRoundId = bookingShowFloors.stream()
                .collect(Collectors.groupingBy(
                        bookingShowFloor -> bookingShowFloor.getBooking().getRoundId(),
                        Collectors.mapping(bookingShowFloor -> showFloorMapById.get(bookingShowFloor.getShowFloorId()), Collectors.toList())
                ));
    }

    public Long getBookingFloorCountByRoundIdAndShowFloorGradeId(Long roundId, Long showFloorGradeId) {

        Map<Long, Long> bookingShowFloorCntMapByShowFloorGradeId = bookingShowFloorsMapByRoundId.getOrDefault(roundId, new ArrayList<>()).stream()
                .collect(Collectors.groupingBy(ShowFloor::getShowFloorGradeId, Collectors.counting()));

        return bookingShowFloorCntMapByShowFloorGradeId.getOrDefault(showFloorGradeId, 0L);
    }
}
