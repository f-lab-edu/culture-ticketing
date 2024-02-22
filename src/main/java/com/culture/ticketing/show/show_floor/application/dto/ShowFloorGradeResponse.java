package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import lombok.Getter;

@Getter
public class ShowFloorGradeResponse {

    private final Long showFloorGradeId;
    private final String showFloorGradeName;
    private final int price;

    public ShowFloorGradeResponse(ShowFloorGrade showFloorGrade) {
        this.showFloorGradeId = showFloorGrade.getShowFloorGradeId();
        this.showFloorGradeName = showFloorGrade.getShowFloorGradeName();
        this.price = showFloorGrade.getPrice();
    }
}
