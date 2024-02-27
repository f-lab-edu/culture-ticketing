package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowFloorResponse {

    private final Long showFloorId;
    private final String showFloorName;
    private final int count;

    @Builder
    public ShowFloorResponse(Long showFloorId, String showFloorName, int count) {
        this.showFloorId = showFloorId;
        this.showFloorName = showFloorName;
        this.count = count;
    }

    public static ShowFloorResponse from(ShowFloor showFloor) {
        return ShowFloorResponse.builder()
                .showFloorId(showFloor.getShowFloorId())
                .showFloorName(showFloor.getShowFloorName())
                .count(showFloor.getCount())
                .build();
    }
}
