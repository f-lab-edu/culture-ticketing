package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    @NotNull
    private Long showSeatGradeId;
    @NotNull
    private Long seatId;
    private boolean isHidden;

    public ShowSeat toEntity() {
        return ShowSeat.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatId(seatId)
                .isHidden(isHidden)
                .build();
    }
}
