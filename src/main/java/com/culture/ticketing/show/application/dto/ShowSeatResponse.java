package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Getter;

@Getter
public class ShowSeatResponse {

    private final Long showSeatId;
    private final String showSeatRow;
    private final int showSeatNumber;
    private final Boolean isAvailable;

    public ShowSeatResponse(ShowSeat showSeat, Boolean isAvailable) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
        this.isAvailable = isAvailable;
    }
}
