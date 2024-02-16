package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceSeatSaveRequest {

    private int seatRow;
    private int seatNumber;
    private Long areaId;

    @Builder
    public PlaceSeatSaveRequest(int seatRow, int seatNumber, Long areaId) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.areaId = areaId;
    }

    public Seat toEntity() {
        return Seat.builder()
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .areaId(areaId)
                .build();
    }
}
