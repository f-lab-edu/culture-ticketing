package com.culture.ticketing.show.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowSeatCountResponse {

    private final Long showAreaGradeId;
    private final String showAreaGradeName;
    private final int price;
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
