package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "장소 응답 DTO")
@Getter
public class PlaceResponse {

    @Schema(description = "장소 아이디")
    private final Long placeId;
    @Schema(description = "장소명")
    private final String placeName;
    @Schema(description = "주소")
    private final String address;
    @Schema(description = "위도")
    private final BigDecimal latitude;
    @Schema(description = "경도")
    private final BigDecimal longitude;

    public PlaceResponse(Place place) {
        this.placeId = place.getPlaceId();
        this.placeName = place.getPlaceName();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }
}
