package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ShowSeatGradeWithCountResponse {

    private final Long showSeatGradeId;
    private final Long availableSeatsCount;

    public ShowSeatGradeWithCountResponse(Long showSeatGradeId, Long availableSeatsCount) {
        this.showSeatGradeId = showSeatGradeId;
        this.availableSeatsCount = availableSeatsCount;
    }

    public static List<ShowSeatGradeWithCountResponse> from(Map<Long, Long> availableShowSeatCntMapByShowSeatGradeId) {
        return availableShowSeatCntMapByShowSeatGradeId.keySet().stream()
                .map(showSeatGradeId -> new ShowSeatGradeWithCountResponse(showSeatGradeId, availableShowSeatCntMapByShowSeatGradeId.get(showSeatGradeId)))
                .collect(Collectors.toList());
    }
}
