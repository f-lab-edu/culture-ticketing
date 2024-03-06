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

    public ShowSeatResponse(ShowSeat showSeat) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
        this.isAvailable = true;
    }

    public ShowSeatResponse getAvailabilityUpdatedShowSeatResponse(Boolean isAvailable) {

        return ShowSeatResponse.builder()
                .showSeatId(showSeatId)
                .showSeatRow(showSeatRow)
                .showSeatNumber(showSeatNumber)
                .isAvailable(isAvailable)
                .build();
    }
}
