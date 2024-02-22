package com.culture.ticketing.show.show_floor.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공연 플로어 등급별 예약 가능 좌석 수 응답 DTO")
@Getter
public class ShowFloorGradeWithCountResponse {

    @Schema(description = "공연 플로어 등급 아이디")
    private final Long showFloorGradeId;
    @Schema(description = "공연 플로어 등급명")
    private final String showFloorGradeName;
    @Schema(description = "가격")
    private final int price;
    @Schema(description = "예약 가능 좌석 수")
    private final Long availableFloorCount;

    public ShowFloorGradeWithCountResponse(ShowFloorGradeResponse showFloorGrade, Long availableFloorCount) {
        this.showFloorGradeId = showFloorGrade.getShowFloorGradeId();
        this.showFloorGradeName = showFloorGrade.getShowFloorGradeName();
        this.price = showFloorGrade.getPrice();
        this.availableFloorCount = availableFloorCount;
    }
}
