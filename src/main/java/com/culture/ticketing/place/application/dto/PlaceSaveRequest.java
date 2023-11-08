package com.culture.ticketing.place.application.dto;

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

}
