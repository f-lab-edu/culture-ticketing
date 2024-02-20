package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowFloorGradeSaveRequest {

    private String showFloorGradeName;
    private int price;
    private Long showId;

    @Builder
    public ShowFloorGradeSaveRequest(String showFloorGradeName, int price, Long showId) {
        this.showFloorGradeName = showFloorGradeName;
        this.price = price;
        this.showId = showId;
    }

    public ShowFloorGrade toEntity() {
        return ShowFloorGrade.builder()
                .showFloorGradeName(showFloorGradeName)
                .price(price)
                .showId(showId)
                .build();
    }
}
