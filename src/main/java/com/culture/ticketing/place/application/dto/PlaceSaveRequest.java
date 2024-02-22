package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "장소 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class PlaceSaveRequest {

    @Schema(description = "장소명")
    private String placeName;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "위도")
    private BigDecimal latitude;
    @Schema(description = "경도")
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
