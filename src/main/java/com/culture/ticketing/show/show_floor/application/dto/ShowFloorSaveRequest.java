package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 플로어 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowFloorSaveRequest {

    @Schema(description = "공연 플로어명")
    private String showFloorName;
    @Schema(description = "입장 가능 수")
    private int count;
    @Schema(description = "공연 플로어 등급 아이디")
    private Long showFloorGradeId;

    @Builder
    public ShowFloorSaveRequest(String showFloorName, int count, Long showFloorGradeId) {
        this.showFloorName = showFloorName;
        this.count = count;
        this.showFloorGradeId = showFloorGradeId;
    }

    public ShowFloor toEntity() {
        return ShowFloor.builder()
                .showFloorName(showFloorName)
                .count(count)
                .showFloorGradeId(showFloorGradeId)
                .build();
    }
}
