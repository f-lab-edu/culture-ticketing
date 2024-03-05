package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;
import lombok.Getter;

@Getter
public class ShowAreaGradeResponse {

    private final Long showAreaGradeId;
    private final String showAreaGradeName;
    private final int price;

    public ShowAreaGradeResponse(ShowAreaGrade showAreaGrade) {
        this.showAreaGradeId = showAreaGrade.getShowAreaGradeId();
        this.showAreaGradeName = showAreaGrade.getShowAreaGradeName();
        this.price = showAreaGrade.getPrice();
    }
}
