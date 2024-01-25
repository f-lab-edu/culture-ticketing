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
    private Long showSeatGradeId;

    @Builder
    public ShowFloorSaveRequest(String showFloorName, int count, Long showSeatGradeId) {
        this.showFloorName = showFloorName;
        this.count = count;
        this.showSeatGradeId = showSeatGradeId;
    }

    public ShowFloor toEntity() {
        return ShowFloor.builder()
                .showFloorName(showFloorName)
                .count(count)
                .showSeatGradeId(showSeatGradeId)
                .build();
    }
}
