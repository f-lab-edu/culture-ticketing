package com.culture.ticketing.show.show_floor.application.dto;

import lombok.Getter;

@Getter
public class ShowFloorGradeWithCountResponse {

    private final Long showFloorGradeId;
    private final String showFloorGradeName;
    private final int price;
    private final Long availableFloorCount;

    public ShowFloorGradeWithCountResponse(ShowFloorGradeResponse showFloorGrade, Long availableFloorCount) {
        this.showFloorGradeId = showFloorGrade.getShowFloorGradeId();
        this.showFloorGradeName = showFloorGrade.getShowFloorGradeName();
        this.price = showFloorGrade.getPrice();
        this.availableFloorCount = availableFloorCount;
    }
}
