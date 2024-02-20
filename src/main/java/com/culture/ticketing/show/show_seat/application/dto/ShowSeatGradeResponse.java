package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ShowSeatGradeResponse {

    private final Long showSeatGradeId;
    private final String showSeatGradeName;
    private final int price;

    @Builder
    public ShowSeatGradeResponse(Long showSeatGradeId, String showSeatGradeName, int price) {
        this.showSeatGradeId = showSeatGradeId;
        this.showSeatGradeName = showSeatGradeName;
        this.price = price;
    }

    public ShowSeatGradeResponse(ShowSeatGrade showSeatGrade) {
        this.showSeatGradeId = showSeatGrade.getShowSeatGradeId();
        this.showSeatGradeName = showSeatGrade.getShowSeatGradeName();
        this.price = showSeatGrade.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowSeatGradeResponse that = (ShowSeatGradeResponse) o;
        return price == that.price && showSeatGradeId.equals(that.showSeatGradeId) && showSeatGradeName.equals(that.showSeatGradeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSeatGradeId, showSeatGradeName, price);
    }
}
