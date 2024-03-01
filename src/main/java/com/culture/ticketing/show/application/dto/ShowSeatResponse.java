package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowSeatResponse {

    private final Long showSeatId;
    private final String showSeatRow;
    private final int showSeatNumber;
    private final Boolean isAvailable;

    @Builder
    public ShowSeatResponse(Long showSeatId, String showSeatRow, int showSeatNumber, Boolean isAvailable) {
        this.showSeatId = showSeatId;
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
        this.isAvailable = isAvailable;
    }

    public ShowSeatResponse(ShowSeat showSeat, Boolean isAvailable) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
        this.isAvailable = isAvailable;
    }
}
