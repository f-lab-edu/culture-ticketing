package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공연 구역 응답 DTO")
@Getter
public class ShowAreaResponse {

    @Schema(description = "공연 구역 아이디")
    private final Long showAreaId;
    @Schema(description = "공연 구역명")
    private final String showAreaName;
    @Schema(description = "공연 구역 등급 아이디")
    private final Long showAreaGradeId;
    @Schema(description = "공연 구역 등급명")
    private final String showAreaGradeName;
    @Schema(description = "가격")
    private final int price;

    public ShowAreaResponse(ShowArea showArea, ShowAreaGradeResponse showAreaGrade) {
        this.showAreaId = showArea.getShowAreaId();
        this.showAreaName = showArea.getShowAreaName();
        this.showAreaGradeId = showArea.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
    }
}
