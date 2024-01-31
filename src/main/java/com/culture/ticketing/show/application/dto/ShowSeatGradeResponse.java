package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowSeatGradeResponse {

    private final Long showSeatGradeId;
    private final String seatGrade;
    private final int price;

    @Builder
    public ShowSeatGradeResponse(Long showSeatGradeId, String seatGrade, int price) {
        this.showSeatGradeId = showSeatGradeId;
        this.seatGrade = seatGrade;
        this.price = price;
    }

    public ShowSeatGradeResponse(ShowSeatGrade showSeatGrade) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.seatGrade = showSeatGrade.getSeatGrade();
        this.price = showSeatGrade.getPrice();
    }
}
