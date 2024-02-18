package com.culture.ticketing.show.show_floor.application.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<ShowFloorGradeWithCountResponse> from(Map<ShowFloorGradeResponse, Long> availableShowFloorCntMapByShowFloorGrade) {
        return availableShowFloorCntMapByShowFloorGrade.keySet().stream()
                .map(showFloorGrade -> new ShowFloorGradeWithCountResponse(showFloorGrade, availableShowFloorCntMapByShowFloorGrade.get(showFloorGrade)))
                .collect(Collectors.toList());
    }
}
