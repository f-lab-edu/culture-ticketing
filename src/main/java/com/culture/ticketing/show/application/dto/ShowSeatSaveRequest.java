package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    private String showSeatRow;
    private int showSeatNumber;
    private Long showAreaId;

    @Builder
    public ShowSeatSaveRequest(String showSeatRow, int showSeatNumber, Long showAreaId) {
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
        this.showAreaId = showAreaId;
    }

    public ShowSeat toEntity() {
        return ShowSeat.builder()
                .showSeatRow(showSeatRow)
                .showSeatNumber(showSeatNumber)
                .showAreaId(showAreaId)
                .build();
    }
}
