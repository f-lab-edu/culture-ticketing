package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import com.culture.ticketing.show.domain.ShowSeat;
import com.culture.ticketing.show.round_performer.exception.RoundNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RoundsShowSeatCountsResponse {

    private final List<RoundShowSeatCountsResponse> roundShowSeatCounts;

    public RoundsShowSeatCountsResponse(List<Long> roundIds, ShowSeatCountsResponse showSeatCounts,
                                        List<BookingShowSeat> bookingShowSeats, List<ShowSeat> showSeatsInBooking) {
        Map<Long, ShowSeat> showSeatMapById = showSeatsInBooking.stream()
                .collect(Collectors.toMap(ShowSeat::getShowSeatId, Function.identity()));

        Map<Long, List<ShowSeat>> showSeatsMapByRoundId = bookingShowSeats.stream()
                .collect(Collectors.groupingBy(BookingShowSeat::getRoundId,
                        Collectors.mapping(bookingShowSeat -> showSeatMapById.get(bookingShowSeat.getShowSeatId()), Collectors.toList())));

        this.roundShowSeatCounts = roundIds.stream()
                .map(roundId -> {
                    ShowSeatCountsResponse newShowSeatCounts = showSeatCounts.copy();
                    newShowSeatCounts.minusShowSeatCount(showSeatsMapByRoundId.getOrDefault(roundId, List.of()));

                    return new RoundShowSeatCountsResponse(roundId, newShowSeatCounts);
                })
                .collect(Collectors.toList());
    }

    public ShowSeatCountsResponse getShowSeatCountsByRoundId(Long roundId) {
        return this.roundShowSeatCounts.stream()
                .filter(roundShowSeatCount -> roundShowSeatCount.getRoundId().equals(roundId))
                .findAny()
                .orElseThrow(() -> {
                    throw new RoundNotFoundException(roundId);
                })
                .getShowSeatCounts();
    }
}
