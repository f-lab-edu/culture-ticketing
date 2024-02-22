package com.culture.ticketing.show.show_seat.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공연 좌석 등급별 예약 가능 좌석 수 응답 DTO")
@Getter
public class ShowSeatGradeWithCountResponse {

    @Schema(description = "공연 좌석 등급 아이디")
    private final Long showSeatGradeId;
    @Schema(description = "공연 좌석 등급명")
    private final String showSeatGradeName;
    @Schema(description = "가격")
    private final int price;
    @Schema(description = "예약 가능 좌석 수")
    private final Long availableSeatsCount;

    public ShowSeatGradeWithCountResponse(ShowSeatGradeResponse showSeatGrade, Long availableSeatsCount) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.showSeatGradeName = showSeatGrade.getShowSeatGradeName();
        this.price = showSeatGrade.getPrice();
        this.availableSeatsCount = availableSeatsCount;
    }
}
