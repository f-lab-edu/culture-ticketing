package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.domain.ShowSeatGrade

class ShowSeatGradeFixtures {

    static ShowSeatGrade createShowSeatGrade(Map map = [:]) {
        return ShowSeatGrade.builder()
                .showSeatGradeId(map.getOrDefault("showSeatGradeId", 1L) as Long)
                .seatGrade(map.getOrDefault("seatGrade", "VIP") as String)
                .price(map.getOrDefault("price", 100000) as Integer)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .build();
    }

    static ShowSeatGradeResponse createShowSeatGradeResponse(Map map = [:]) {
        return ShowSeatGradeResponse.builder()
                .showSeatGradeId(map.getOrDefault("showSeatGradeId", 1L) as Long)
                .seatGrade(map.getOrDefault("seatGrade", "VIP") as String)
                .price(map.getOrDefault("price", 100000) as Integer)
                .build();
    }
}
