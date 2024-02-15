package com.culture.ticketing.booking.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class BookingShowFloorSaveRequest {

    private Long showFloorId;
    private int entryOrder;

    public BookingShowFloorSaveRequest(Long showFloorId, int entryOrder) {
        this.showFloorId = showFloorId;
        this.entryOrder = entryOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingShowFloorSaveRequest that = (BookingShowFloorSaveRequest) o;
        return entryOrder == that.entryOrder && showFloorId.equals(that.showFloorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showFloorId, entryOrder);
    }
}
