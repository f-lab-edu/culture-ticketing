package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공연 구역 등급 응답 DTO")
@Getter
public class ShowAreaGradeResponse {

    @Schema(description = "공연 구역 등급 아이디")
    private final Long showAreaGradeId;
    @Schema(description = "공연 구역 등급명")
    private final String showAreaGradeName;
    @Schema(description = "가격")
    private final int price;

    public ShowAreaGradeResponse(ShowAreaGrade showAreaGrade) {
        this.showAreaGradeId = showAreaGrade.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
    }
}
