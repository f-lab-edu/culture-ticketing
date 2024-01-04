package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PlaceSaveRequest {

    private String placeName;
    @NotBlank(message = "공연 장소 주소를 입력해주세요.")
    private String address;
    @NotNull
    @Positive
    private BigDecimal latitude;
    @NotNull
    @Positive
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
