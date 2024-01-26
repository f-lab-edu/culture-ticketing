package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowFloor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ShowFloorSaveRequest {

    @NotBlank
    private String showFloorName;
    @Positive
    private int count;
    @NotNull
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
