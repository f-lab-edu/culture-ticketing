package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Seat;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlaceSeatMapBySeatIdResponse {

    private final Map<Long, Seat> seatMapBySeatId;

    public PlaceSeatMapBySeatIdResponse(List<Seat> seats) {
        this.seatMapBySeatId = seats.stream()
                .collect(Collectors.toMap(Seat::getSeatId, Function.identity()));
    }

    public Seat getById(Long seatId) {
        return seatMapBySeatId.get(seatId);
    }
}
