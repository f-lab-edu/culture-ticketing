package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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

    public Place toEntity() {
        return Place.builder()
                .placeName(placeName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
