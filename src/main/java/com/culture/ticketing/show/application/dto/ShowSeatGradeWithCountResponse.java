package com.culture.ticketing.show.application.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<ShowSeatGradeWithCountResponse> from(Map<ShowSeatGradeResponse, Long> availableShowSeatCntMapByShowSeatGrade) {
        return availableShowSeatCntMapByShowSeatGrade.keySet().stream()
                .map(showSeatGrade -> new ShowSeatGradeWithCountResponse(showSeatGrade, availableShowSeatCntMapByShowSeatGrade.get(showSeatGrade)))
                .collect(Collectors.toList());
    }
}
