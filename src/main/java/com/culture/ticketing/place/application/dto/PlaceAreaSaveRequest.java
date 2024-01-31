package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Area;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceAreaSaveRequest {

    private String areaName;
    private Long placeId;

    @Builder
    public PlaceAreaSaveRequest(String areaName, Long placeId) {
        this.areaName = areaName;
        this.placeId = placeId;
    }

    public Area toEntity() {
        return Area.builder()
                .areaName(areaName)
                .placeId(placeId)
                .build();
    }
}
