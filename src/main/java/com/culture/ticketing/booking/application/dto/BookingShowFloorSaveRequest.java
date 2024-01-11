package com.culture.ticketing.booking.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class BookingShowFloorSaveRequest {

    @NotNull
    private Long showFloorId;
    @Positive
    private int entryOrder;

}
