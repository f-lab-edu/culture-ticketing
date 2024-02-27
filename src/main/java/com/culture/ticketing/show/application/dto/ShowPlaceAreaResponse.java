package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Area;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowPlaceAreaResponse {

    private final Long areaId;
    private final String areaName;

    @Builder
    public ShowPlaceAreaResponse(Long areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }
    public static ShowPlaceAreaResponse from(Area area) {
        return ShowPlaceAreaResponse.builder()
                .areaId(area.getAreaId())
                .areaName(area.getAreaName())
                .build();
    }
}
