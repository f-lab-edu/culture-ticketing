package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Schema(description = "공연 좌석 등급 응답 DTO")
@Getter
public class ShowSeatGradeResponse {

    @Schema(description = "공연 좌석 등급 아이디")
    private final Long showSeatGradeId;
    @Schema(description = "공연 좌석 등급명")
    private final String showSeatGradeName;
    @Schema(description = "가격")
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
