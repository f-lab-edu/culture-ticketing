package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Seat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class PlaceSeatSaveRequest {

    private int coordinateX;
    private int coordinateY;
    @Positive
    private int seatRow;
    @Positive
    private int seatNumber;
    @NotNull
    private Long areaId;

    public Seat toEntity() {
        return Seat.builder()
                .coordinateX(coordinateX)
                .coordinateY(coordinateY)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .areaId(areaId)
                .build();
    }
}
