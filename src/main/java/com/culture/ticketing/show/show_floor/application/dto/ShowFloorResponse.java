package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "공연 플로어 응답 DTO")
@Getter
public class ShowFloorResponse {

    @Schema(description = "공연 플로어 아이디")
    private final Long showFloorId;
    @Schema(description = "공연 플로어명")
    private final String showFloorName;
    @Schema(description = "입장 가능 수")
    private final int count;

    @Builder
    public ShowFloorResponse(Long showFloorId, String showFloorName, int count) {
        this.showFloorId = showFloorId;
        this.showFloorName = showFloorName;
        this.count = count;
    }

    public static ShowFloorResponse from(ShowFloor showFloor) {
        return ShowFloorResponse.builder()
                .showFloorId(showFloor.getShowFloorId())
                .showFloorName(showFloor.getShowFloorName())
                .count(showFloor.getCount())
                .build();
    }
}
