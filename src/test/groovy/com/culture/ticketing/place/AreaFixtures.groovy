package com.culture.ticketing.place

import com.culture.ticketing.place.domain.Area

class AreaFixtures {

    static Area createArea(Map map = [:]) {
        return Area.builder()
                .areaId(map.getOrDefault("areaId", null) as Long)
                .areaName(map.getOrDefault("areaName", "구역명") as String)
                .placeId(map.getOrDefault("placeId", 1L) as Long)
                .build();
    }
}
