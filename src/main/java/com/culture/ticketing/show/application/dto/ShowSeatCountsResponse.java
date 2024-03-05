package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowSeatCountsResponse {

    private final List<ShowSeatCountResponse> showSeatCounts;
    private final ShowAreasResponse showAreas;

    @Builder
    public ShowSeatCountsResponse(List<ShowSeatCountResponse> showSeatCounts, ShowAreasResponse showAreas) {
        this.showSeatCounts = showSeatCounts;
        this.showAreas = showAreas;
    }

    public ShowSeatCountsResponse(List<ShowSeat> showSeats, ShowAreasResponse showAreas, ShowAreaGradesResponse showAreaGrades) {

        Map<Long, Long> showSeatCntMapByShowAreaGradeId = showSeats.stream()
                .collect(Collectors.groupingBy(showSeat -> showAreas.getByShowAreaId(showSeat.getShowAreaId()).getShowAreaGradeId(), Collectors.counting()));

        this.showSeatCounts = showAreaGrades.getShowAreaGrades().stream()
                .map(showAreaGrade -> new ShowSeatCountResponse(showAreaGrade, showSeatCntMapByShowAreaGradeId.getOrDefault(showAreaGrade.getShowAreaGradeId(), 0L)))
                .collect(Collectors.toList());
        this.showAreas = showAreas;
    }

    public ShowSeatCountsResponse getSubtractedShowSeatCounts(List<ShowSeat> showSeats) {

        Map<Long, Long> showSeatCntMapByShowAreaGradeId = showSeats.stream()
                .collect(Collectors.groupingBy(showSeat -> showAreas.getByShowAreaId(showSeat.getShowAreaId()).getShowAreaGradeId(), Collectors.counting()));

        return ShowSeatCountsResponse.builder()
                .showSeatCounts(this.showSeatCounts.stream()
                        .map(showSeatCount -> ShowSeatCountResponse.builder()
                                .showAreaGradeId(showSeatCount.getShowAreaGradeId())
                                .showAreaGradeName(showSeatCount.getShowAreaGradeName())
                                .price(showSeatCount.getPrice())
                                .availableSeatCount(showSeatCount.getAvailableSeatCount() - showSeatCntMapByShowAreaGradeId.getOrDefault(showSeatCount.getShowAreaGradeId(), 0L))
                                .build())
                        .collect(Collectors.toList()))
                .showAreas(showAreas)
                .build();
    }

    public List<ShowSeatCountResponse> getShowSeatCounts() {
        return Collections.unmodifiableList(this.showSeatCounts);
    }
}
