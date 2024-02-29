package com.culture.ticketing.show.application.dto;

import lombok.Getter;

@Getter
public class ShowAreaGradeWithSeatCountResponse {

    private final Long showAreaGradeId;
    private final String showAreaGradeName;
    private final int price;
    private final Long availableSeatCount;

    public ShowAreaGradeWithSeatCountResponse(ShowAreaGradeResponse showAreaGrade, Long availableSeatCount) {
        this.showAreaGradeId = showAreaGrade.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
        this.availableSeatCount = availableSeatCount;
    }
}
