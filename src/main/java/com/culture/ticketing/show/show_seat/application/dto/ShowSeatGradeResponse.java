package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import lombok.Getter;

@Getter
public class ShowSeatGradeResponse {

    private final Long showSeatGradeId;
    private final String showSeatGradeName;
    private final int price;

    public ShowSeatGradeResponse(ShowSeatGrade showSeatGrade) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.showSeatGradeName = showSeatGrade.getShowSeatGradeName();
        this.price = showSeatGrade.getPrice();
    }
}
