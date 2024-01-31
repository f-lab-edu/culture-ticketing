package com.culture.ticketing.booking.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingShowFloorSaveRequest {

    private Long showFloorId;
    private int entryOrder;

    public BookingShowFloorSaveRequest(Long showFloorId, int entryOrder) {
        this.showFloorId = showFloorId;
        this.entryOrder = entryOrder;
    }
}
