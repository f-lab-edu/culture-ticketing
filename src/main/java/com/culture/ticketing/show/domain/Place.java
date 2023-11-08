package com.culture.ticketing.show.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor
public class Place {

    private String placeName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Builder
    public Place(String placeName, String address, BigDecimal latitude, BigDecimal longitude) {
        this.placeName = placeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
