package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;

import java.util.ArrayList;
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

    public ShowSeatCountsResponse copy() {
        List<ShowSeatCountResponse> newShowSeatCounts = new ArrayList<>();
        for (ShowSeatCountResponse showSeatCount : showSeatCounts) {
            newShowSeatCounts.add(showSeatCount.copy());
        }
        return ShowSeatCountsResponse.builder()
                .showSeatCounts(newShowSeatCounts)
                .showAreas(showAreas)
                .build();
    }

    public void minusShowSeatCount(List<ShowSeat> showSeats) {

        Map<Long, Long> showSeatCntMapByShowAreaGradeId = showSeats.stream()
                .collect(Collectors.groupingBy(showSeat -> showAreas.getByShowAreaId(showSeat.getShowAreaId()).getShowAreaGradeId(), Collectors.counting()));

        showSeatCounts.forEach(showSeatCount -> showSeatCount.minusAvailableSeatCount(showSeatCntMapByShowAreaGradeId.getOrDefault(showSeatCount.getShowAreaGradeId(), 0L)));
    }

    public List<ShowSeatCountResponse> getShowSeatCounts() {
        return Collections.unmodifiableList(this.showSeatCounts);
    }
}
