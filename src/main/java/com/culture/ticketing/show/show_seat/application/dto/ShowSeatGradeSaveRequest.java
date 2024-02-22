package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 좌석 등급 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowSeatGradeSaveRequest {

    @Schema(description = "공연 좌석 등급명")
    private String showSeatGradeName;
    @Schema(description = "가격")
    private int price;
    @Schema(description = "공연 아이디")
    private Long showId;

    @Builder
    public ShowSeatGradeSaveRequest(String showSeatGradeName, int price, Long showId) {
        this.showSeatGradeName = showSeatGradeName;
        this.price = price;
        this.showId = showId;
    }

    public ShowSeatGrade toEntity() {
        return ShowSeatGrade.builder()
                .showSeatGradeName(showSeatGradeName)
                .price(price)
                .showId(showId)
                .build();
    }
}
