package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Getter;

@Getter
public class ShowSeatGradeResponse {

    private final Long showSeatGradeId;
    private final String seatGrade;
    private final int price;

    public ShowSeatGradeResponse(ShowSeatGrade showSeatGrade) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.seatGrade = showSeatGrade.getSeatGrade();
        this.price = showSeatGrade.getPrice();
    }
}
