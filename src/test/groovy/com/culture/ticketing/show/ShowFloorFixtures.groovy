package com.culture.ticketing.show

import com.culture.ticketing.show.domain.ShowFloor;

class ShowFloorFixtures {

    static ShowFloor createShowFloor(Map map = [:]) {
        return ShowFloor.builder()
                .showFloorId(map.getOrDefault("showFloorId", 1L) as Long)
                .showFloorName(map.getOrDefault("showFloorName", "F1") as String)
                .count(map.getOrDefault("count", 700) as Integer)
                .showSeatGradeId(map.getOrDefault("showSeatGradeId", 1L) as Long)
                .build();
    }
}
