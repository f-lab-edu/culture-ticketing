package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Getter;

@Getter
public class ShowSeatGradeWithCountResponse {

    private final Long showSeatGradeId;
    private final String seatGrade;
    private final int availableSeatsCount;

    public ShowSeatGradeWithCountResponse(Long showSeatGradeId, String seatGrade, int availableSeatsCount) {
        this.showSeatGradeId = showSeatGradeId;
        this.seatGrade = seatGrade;
        this.availableSeatsCount = availableSeatsCount;
    }
}
