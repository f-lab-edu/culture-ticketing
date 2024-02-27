package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowFloorCountMapByShowFloorGradeIdResponse {

    private final Map<Long, Long> showFloorCountMapByShowFloorGradeId;

    public ShowFloorCountMapByShowFloorGradeIdResponse(List<ShowFloor> showFloors) {
        this.showFloorCountMapByShowFloorGradeId = showFloors.stream()
                .collect(Collectors.groupingBy(ShowFloor::getShowFloorGradeId, Collectors.summingLong(ShowFloor::getCount)));
    }

    public Long getShowFloorCountByShowFloorGradeId(Long showFloorGradeId) {
        return showFloorCountMapByShowFloorGradeId.getOrDefault(showFloorGradeId, 0L);
    }
}
