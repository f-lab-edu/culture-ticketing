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
    @NotBlank(message = "좌석 구역을 입력해주세요.")
    private String area;
    @Positive
    private int seatRow;
    @Positive
    private int seatNumber;
    @NotNull
    private Long placeId;

    public Seat toEntity() {
        return Seat.builder()
                .coordinateX(coordinateX)
                .coordinateY(coordinateY)
                .area(area)
                .seatRow(seatRow)
                .seatNumber(seatNumber)
                .placeId(placeId)
                .build();
    }
}
