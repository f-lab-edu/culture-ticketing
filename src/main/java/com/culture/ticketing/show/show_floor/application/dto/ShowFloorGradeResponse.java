package com.culture.ticketing.show.show_floor.application.dto;

import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ShowFloorGradeResponse {

    private final Long showFloorGradeId;
    private final String showFloorGradeName;
    private final int price;

    @Builder
    public ShowFloorGradeResponse(Long showFloorGradeId, String showFloorGradeName, int price) {
        this.showFloorGradeId = showFloorGradeId;
        this.showFloorGradeName = showFloorGradeName;
        this.price = price;
    }

    public ShowFloorGradeResponse(ShowFloorGrade showFloorGrade) {
        this.showFloorGradeId = showFloorGrade.getShowFloorGradeId();
        this.showFloorGradeName = showFloorGrade.getShowFloorGradeName();
        this.price = showFloorGrade.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowFloorGradeResponse that = (ShowFloorGradeResponse) o;
        return price == that.price && showFloorGradeId.equals(that.showFloorGradeId) && showFloorGradeName.equals(that.showFloorGradeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showFloorGradeId, showFloorGradeName, price);
    }
}