package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공연 좌석 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    @Schema(description = "공연 좌석 열")
    private String showSeatRow;
    @Schema(description = "공연 좌석 번호")
    private int showSeatNumber;
    @Schema(description = "공연 구역 아이디")
    private Long showAreaId;

    @Builder
    public ShowSeatSaveRequest(String showSeatRow, int showSeatNumber, Long showAreaId) {
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
        this.showAreaId = showAreaId;
    }

    public ShowSeat toEntity() {
        return ShowSeat.builder()
                .showSeatRow(showSeatRow)
                .showSeatNumber(showSeatNumber)
                .showAreaId(showAreaId)
                .build();
    }
}
