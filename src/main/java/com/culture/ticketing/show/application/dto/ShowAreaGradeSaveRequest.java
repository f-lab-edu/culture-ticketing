package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 구역 등급 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowAreaGradeSaveRequest {

    @Schema(description = "공연 구역 등급명")
    private String showAreaGradeName;
    @Schema(description = "가격")
    private int price;
    @Schema(description = "공연 아이디")
    private Long showId;

    @Builder
    public ShowAreaGradeSaveRequest(String showAreaGradeName, int price, Long showId) {
        this.showAreaGradeName = showAreaGradeName;
        this.price = price;
        this.showId = showId;
    }

    public ShowAreaGrade toEntity() {
        return ShowAreaGrade.builder()
                .showAreaGradeName(showAreaGradeName)
                .price(price)
                .showId(showId)
                .build();
    }
}
