package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowSeatCountMapByShowSeatGradeIdResponse {

    private final Map<Long, Long> showSeatCountMapByShowSeatGradeId;

    public ShowSeatCountMapByShowSeatGradeIdResponse(List<ShowSeat> showSeats) {
        this.showSeatCountMapByShowSeatGradeId = showSeats.stream()
                .collect(Collectors.groupingBy(ShowSeat::getShowSeatGradeId, Collectors.counting()));
    }

    public Long getShowSeatCountByShowSeatGradeId(Long showSeatGradeId) {

        return showSeatCountMapByShowSeatGradeId.getOrDefault(showSeatGradeId, 0L);
    }
}
