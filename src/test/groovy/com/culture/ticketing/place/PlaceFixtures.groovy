package com.culture.ticketing.place

import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.domain.Place;

import java.math.BigDecimal;

class PlaceFixtures {

    static Place createPlace(Long placeId) {
        return Place.builder()
                .placeId(placeId)
                .placeName("장소" + placeId)
                .address("서울특별시 " + placeId)
                .latitude(new BigDecimal(placeId))
                .longitude(new BigDecimal(placeId))
                .build();
    }

    static PlaceResponse createPlaceResponse(Long placeId) {
        return new PlaceResponse(createPlace(placeId));
    }
}
