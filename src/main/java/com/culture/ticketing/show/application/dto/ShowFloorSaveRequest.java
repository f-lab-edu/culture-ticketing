package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowFloor;
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
    private int coordinateX;
    private int coordinateY;
    @Positive
    private int count;
    @NotNull
    private Long showSeatGradeId;

    public ShowFloor toEntity() {
        return ShowFloor.builder()
                .showFloorName(showFloorName)
                .coordinateX(coordinateX)
                .coordinateY(coordinateY)
                .count(count)
                .showSeatGradeId(showSeatGradeId)
                .build();
    }
}
