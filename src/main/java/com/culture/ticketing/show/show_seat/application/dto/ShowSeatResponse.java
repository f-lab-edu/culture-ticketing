package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.place.domain.Seat;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import lombok.Getter;

@Getter
public class ShowSeatResponse {

    private final Long showSeatId;
    private final Long seatId;
    private final int seatRow;
    private final int seatNumber;
    private final Long showSeatGradeId;
    private final String showSeatGradeName;
    private final int price;
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
