package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class PlaceSeatSaveRequest {

    @Positive
    private int seatRow;
    @Positive
    private int seatNumber;
    @NotNull
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
