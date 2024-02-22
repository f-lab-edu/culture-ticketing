package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 플로어 등급 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowFloorGradeSaveRequest {

    @Schema(description = "공연 플로어 등급명")
    private String showFloorGradeName;
    @Schema(description = "가격")
    private int price;
    @Schema(description = "공연 아이디")
    private Long showId;

    @Builder
    public ShowFloorGradeSaveRequest(String showFloorGradeName, int price, Long showId) {
        this.showFloorGradeName = showFloorGradeName;
        this.price = price;
        this.showId = showId;
    }

    public ShowFloorGrade toEntity() {
        return ShowFloorGrade.builder()
                .showFloorGradeName(showFloorGradeName)
                .price(price)
                .showId(showId)
                .build();
    }
}
