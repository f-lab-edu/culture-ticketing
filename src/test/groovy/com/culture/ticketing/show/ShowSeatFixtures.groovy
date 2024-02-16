package com.culture.ticketing.show;

import com.culture.ticketing.show.domain.ShowSeat;

class ShowSeatFixtures {

    static ShowSeat createShowSeat(Map map = [:]) {
        return ShowSeat.builder()
                .showSeatId(map.getOrDefault("showSeatId", 1L) as Long)
                .showSeatGradeId(map.getOrDefault("showSeatGradeId", 1L) as Long)
                .seatId(map.getOrDefault("seatId", 1L) as Long)
                .build();
    }
}
