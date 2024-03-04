package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PlaceResponse {

    private final Long placeId;
    private final String placeName;
    private final String address;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public PlaceResponse(Place place) {
        this.placeId = place.getPlaceId();
        this.placeName = place.getPlaceName();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }
}
