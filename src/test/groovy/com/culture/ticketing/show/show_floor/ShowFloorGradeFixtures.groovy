package com.culture.ticketing.show.show_floor

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade

class ShowFloorGradeFixtures {

    static ShowFloorGrade createShowFloorGrade(Map map = [:]) {
        return ShowFloorGrade.builder()
                .showFloorGradeId(map.getOrDefault("showFloorGradeId", 1L) as Long)
                .showFloorGradeName(map.getOrDefault("showFloorGradeName", "VIP") as String)
                .price(map.getOrDefault("price", 100000) as Integer)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .build();
    }
}
