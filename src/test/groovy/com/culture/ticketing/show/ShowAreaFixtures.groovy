package com.culture.ticketing.show

import com.culture.ticketing.show.domain.ShowArea

class ShowAreaFixtures {

    static ShowArea createShowArea(Map map = [:]) {
        return ShowArea.builder()
                .showAreaId(map.getOrDefault("showAreaId", null) as Long)
                .showAreaName(map.getOrDefault("showAreaName", "구역명") as String)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .showAreaGradeId(map.getOrDefault("showAreaGradeId", 1L) as Long)
                .build();
    }
}
