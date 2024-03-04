package com.culture.ticketing.show

import com.culture.ticketing.show.domain.ShowAreaGrade

class ShowAreaGradeFixtures {

    static ShowAreaGrade createShowAreaGrade(Map map = [:]) {
        return ShowAreaGrade.builder()
                .showAreaGradeId(map.getOrDefault("showAreaGradeId", null) as Long)
                .showAreaGradeName(map.getOrDefault("showAreaGradeName", "R") as String)
                .price(map.getOrDefault("price", 100000) as Integer)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .build();
    }
}
