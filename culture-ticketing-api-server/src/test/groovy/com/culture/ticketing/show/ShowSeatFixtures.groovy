package com.culture.ticketing.show

import com.culture.ticketing.show.domain.ShowSeat

class ShowSeatFixtures {

    static ShowSeat creatShowSeat(Map map = [:]) {
        return ShowSeat.builder()
                .showSeatId(map.getOrDefault("showSeatId", null) as Long)
                .showSeatRow(map.getOrDefault("showSeatRow", "A") as String)
                .showSeatNumber(map.getOrDefault("showSeatNumber", 1) as Integer)
                .showAreaId(map.getOrDefault("showAreaId", 1L) as Long)
                .build();
    }
}
