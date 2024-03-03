package com.culture.ticketing.show

import com.culture.ticketing.show.domain.Place;

class PlaceFixtures {

    static Place createPlace(Map map = [:]) {
        return Place.builder()
                .placeId(map.getOrDefault("placeId", null) as Long)
                .placeName(map.getOrDefault("placeName", "장소 테스트") as String)
                .address(map.getOrDefault("address", "서울특별시") as String)
                .latitude(map.getOrDefault("latitude", 36.1) as BigDecimal)
                .longitude(map.getOrDefault("longitude", 102.6) as BigDecimal)
                .build();
    }
}
