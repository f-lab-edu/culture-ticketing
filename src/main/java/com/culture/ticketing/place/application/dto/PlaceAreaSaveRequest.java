package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Area;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PlaceAreaSaveRequest {

    private String areaName;
    @NotNull
    private Long placeId;

    public Area toEntity() {
        return Area.builder()
                .areaName(areaName)
                .placeId(placeId)
                .build();
    }
}
