package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "장소 구역 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class PlaceAreaSaveRequest {

    @Schema(description = "구역명")
    private String areaName;
    @Schema(description = "장소 아이디")
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
