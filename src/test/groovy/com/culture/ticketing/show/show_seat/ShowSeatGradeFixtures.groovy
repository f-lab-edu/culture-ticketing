package com.culture.ticketing.show.show_seat

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade

class ShowSeatGradeFixtures {

    static ShowSeatGrade createShowSeatGrade(Map map = [:]) {
        return ShowSeatGrade.builder()
                .showSeatGradeId(map.getOrDefault("showSeatGradeId", null) as Long)
                .showSeatGradeName(map.getOrDefault("showSeatGradeName", "R") as String)
                .price(map.getOrDefault("price", 100000) as Integer)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .build();
    }
}
