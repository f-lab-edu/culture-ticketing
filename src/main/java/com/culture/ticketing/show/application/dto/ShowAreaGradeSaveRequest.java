package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowAreaGradeSaveRequest {

    private String showAreaGradeName;
    private int price;
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
