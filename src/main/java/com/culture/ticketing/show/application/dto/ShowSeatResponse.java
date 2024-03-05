package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "예약 가능 여부")
    private final Boolean isAvailable;

    public ShowSeatResponse(ShowSeat showSeat, Boolean isAvailable) {
        this.showSeatId = showSeat.getShowSeatId();
        this.showSeatRow = showSeat.getShowSeatRow();
        this.showSeatNumber = showSeat.getShowSeatNumber();
        this.isAvailable = isAvailable;
    }
}
