package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공연 좌석 응답 DTO")
@Getter
public class ShowSeatResponse {

    @Schema(description = "공연 좌석 아이디")
    private final Long showSeatId;
    @Schema(description = "좌석 아이디")
    private final Long seatId;
    @Schema(description = "좌석 열")
    private final int seatRow;
    @Schema(description = "좌석 번호")
    private final int seatNumber;
    @Schema(description = "공연 좌석 등급 아이디")
    private final Long showSeatGradeId;
    @Schema(description = "공연 좌석 등급명")
    private final String showSeatGradeName;
    @Schema(description = "가격")
    private final int price;
    @Schema(description = "예약 가능 여부")
    private final Boolean isAvailable;

    public ShowSeatResponse(ShowSeat showSeat, Seat seat, ShowSeatGrade showSeatGrade, Boolean isAvailable) {
        this.showSeatId = showSeat.getShowSeatId();
        this.seatId = showSeat.getSeatId();
        this.seatRow = seat.getSeatRow();
        this.seatNumber = seat.getSeatNumber();
        this.showSeatGradeId = showSeat.getShowSeatGradeId();
        this.showSeatGradeName = showSeatGrade.getShowSeatGradeName();
        this.price = showSeatGrade.getPrice();
        this.isAvailable = isAvailable;
    }
}
