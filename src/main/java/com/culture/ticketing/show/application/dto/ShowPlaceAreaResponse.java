package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "공연 장소 구역 응답 DTO")
@Getter
public class ShowPlaceAreaResponse {

    @Schema(description = "구역 아이디")
    private final Long areaId;
    @Schema(description = "구역명")
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
