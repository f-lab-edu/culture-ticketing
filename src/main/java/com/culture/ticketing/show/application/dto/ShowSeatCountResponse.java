package com.culture.ticketing.show.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "공연 구역 등급별 이용 가능 공연 좌석 수 응답 DTO")
@Getter
public class ShowSeatCountResponse {

    @Schema(description = "공연 구역 등급 아이디")
    private final Long showAreaGradeId;
    @Schema(description = "공연 구역 등급명")
    private final String showAreaGradeName;
    @Schema(description = "가격")
    private final int price;
    @Schema(description = "이용 가능 공연 좌석 수")
    private long availableSeatCount = 0;

    @Builder
    public ShowSeatCountResponse(Long showAreaGradeId, String showAreaGradeName, int price, long availableSeatCount) {
        this.showAreaGradeId = showAreaGradeId;
        this.showAreaGradeName = showAreaGradeName;
        this.price = price;
        this.availableSeatCount = availableSeatCount;
    }

    public ShowSeatCountResponse(ShowAreaGradeResponse showAreaGrade, long availableSeatCount) {
        this.showAreaGradeId = showAreaGrade.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
        this.availableSeatCount = availableSeatCount;
    }

    public ShowSeatCountResponse getSubtractedShowSeatCountResponse(long unavailableSeatCount) {

        return ShowSeatCountResponse.builder()
                .showAreaGradeId(showAreaGradeId)
                .showAreaGradeName(showAreaGradeName)
                .price(price)
                .availableSeatCount(availableSeatCount - unavailableSeatCount)
                .build();
    }
}
