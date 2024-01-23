package com.culture.ticketing.place

import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.domain.Place;

class PlaceFixtures {

    static Place createPlace(Long placeId, String placeName = "장소 테스트", String address = "서울특별시", Double latitude = 36.1, Double longitude = 102.6) {
        return Place.builder()
                .placeId(placeId)
                .placeName(placeName)
                .address(address)
                .latitude(new BigDecimal(latitude))
                .longitude(new BigDecimal(longitude))
                .build();
    }

    static PlaceResponse createPlaceResponse(Long placeId) {
        return new PlaceResponse(createPlace(placeId));
    }
}
