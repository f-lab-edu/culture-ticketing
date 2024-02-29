package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PlaceSaveRequest {

    private String placeName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Builder
    public PlaceSaveRequest(String placeName, String address, BigDecimal latitude, BigDecimal longitude) {
        this.placeName = placeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place toEntity() {
        return Place.builder()
                .placeName(placeName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
