package com.culture.ticketing.place.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "PLACES")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", nullable = false, updatable = false)
    private Long placeId;
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
