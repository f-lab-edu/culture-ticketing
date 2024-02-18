package com.culture.ticketing.show.show_floor

import com.culture.ticketing.show.show_floor.domain.ShowFloor;

class ShowFloorFixtures {

    static ShowFloor createShowFloor(Map map = [:]) {
        return ShowFloor.builder()
                .showFloorId(map.getOrDefault("showFloorId", 1L) as Long)
                .showFloorName(map.getOrDefault("showFloorName", "F1") as String)
                .count(map.getOrDefault("count", 700) as Integer)
                .showFloorGradeId(map.getOrDefault("showFloorGradeId", 1L) as Long)
                .build();
    }
}
