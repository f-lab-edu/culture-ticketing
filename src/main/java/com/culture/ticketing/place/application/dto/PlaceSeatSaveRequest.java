package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Seat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "장소 좌석 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class PlaceSeatSaveRequest {

    @Schema(description = "좌석 열")
    private int seatRow;
    @Schema(description = "좌석 번호")
    private int seatNumber;
    @Schema(description = "구역 아이디")
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
