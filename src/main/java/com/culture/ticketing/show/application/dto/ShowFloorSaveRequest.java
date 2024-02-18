package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowFloor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowFloorSaveRequest {

    private String showFloorName;
    private int count;
    private Long showFloorGradeId;

    @Builder
    public ShowFloorSaveRequest(String showFloorName, int count, Long showFloorGradeId) {
        this.showFloorName = showFloorName;
        this.count = count;
        this.showFloorGradeId = showFloorGradeId;
    }

    public ShowFloor toEntity() {
        return ShowFloor.builder()
                .showFloorName(showFloorName)
                .count(count)
                .showFloorGradeId(showFloorGradeId)
                .build();
    }
}
