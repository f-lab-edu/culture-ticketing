package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowSeatResponse {

    private final Long showSeatId;
    private final String showSeatRow;
    private final int showSeatNumber;

    @Builder
    public ShowSeatResponse(Long showSeatId, String showSeatRow, int showSeatNumber) {
        this.showSeatId = showSeatId;
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
    }

    public ShowSeatResponse(ShowSeat showSeat) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
    }
}
