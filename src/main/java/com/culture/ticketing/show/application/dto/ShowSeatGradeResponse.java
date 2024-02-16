package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ShowSeatGradeResponse {

    private final Long showSeatGradeId;
    private final String seatGrade;
    private final int price;

    @Builder
    public ShowSeatGradeResponse(Long showSeatGradeId, String seatGrade, int price) {
        this.showSeatGradeId = showSeatGradeId;
        this.seatGrade = seatGrade;
        this.price = price;
    }

    public ShowSeatGradeResponse(ShowSeatGrade showSeatGrade) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.seatGrade = showSeatGrade.getSeatGrade();
        this.price = showSeatGrade.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowSeatGradeResponse that = (ShowSeatGradeResponse) o;
        return price == that.price && showSeatGradeId.equals(that.showSeatGradeId) && seatGrade.equals(that.seatGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSeatGradeId, seatGrade, price);
    }
}
