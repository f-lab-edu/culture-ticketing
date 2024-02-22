package com.culture.ticketing.show.show_seat.application.dto;

import lombok.Getter;

@Getter
public class ShowSeatGradeWithCountResponse {

    private final Long showSeatGradeId;
    private final String showSeatGradeName;
    private final int price;
    private final Long availableSeatsCount;

    public ShowSeatGradeWithCountResponse(ShowSeatGradeResponse showSeatGrade, Long availableSeatsCount) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.showSeatGradeName = showSeatGrade.getShowSeatGradeName();
        this.price = showSeatGrade.getPrice();
        this.availableSeatsCount = availableSeatsCount;
    }
}
