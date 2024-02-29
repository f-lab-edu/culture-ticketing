package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import com.culture.ticketing.show.domain.ShowAreaGrade;
import lombok.Getter;

@Getter
public class ShowAreaResponse {

    private final Long showAreaId;
    private final String showAreaName;
    private final Long showAreaGradeId;
    private final String showAreaGradeName;
    private final int price;

    public ShowAreaResponse(ShowArea showArea, ShowAreaGrade showAreaGrade) {
        this.showAreaId = showArea.getShowAreaId();
        this.showAreaName = showArea.getShowAreaName();
        this.showAreaGradeId = showArea.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
    }
}
