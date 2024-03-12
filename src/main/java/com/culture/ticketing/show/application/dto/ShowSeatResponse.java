package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "공연 좌석 응답 DTO")
@Getter
public class ShowSeatResponse {

    @Schema(description = "공연 좌석 아이디")
    private final Long showSeatId;
    @Schema(description = "공연 좌석 열")
    private final String showSeatRow;
    @Schema(description = "공연 좌석 번호")
    private final int showSeatNumber;
    @Schema(description = "공연 구역 아이디")
    private final Long showAreaId;
    @Schema(description = "예약 가능 여부")
    private final Boolean isAvailable;

    @Builder
    public ShowSeatResponse(Long showSeatId, String showSeatRow, int showSeatNumber, Long showAreaId, Boolean isAvailable) {
        this.showSeatId = showSeatId;
        this.showSeatRow = showSeatRow;
        this.showSeatNumber = showSeatNumber;
        this.showAreaId = showAreaId;
        this.isAvailable = isAvailable;
    }

    public ShowSeatResponse(ShowSeat showSeat) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
        this.showAreaId = showSeat.getShowAreaId();
        this.isAvailable = true;
    }

    public ShowSeatResponse getAvailabilityUpdatedShowSeatResponse(Boolean isAvailable) {

        return ShowSeatResponse.builder()
                .showSeatId(showSeatId)
                .showSeatRow(showSeatRow)
                .showSeatNumber(showSeatNumber)
                .showAreaId(showAreaId)
                .isAvailable(isAvailable)
                .build();
    }
}
