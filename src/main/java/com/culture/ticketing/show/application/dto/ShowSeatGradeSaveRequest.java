package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor
public class ShowSeatGradeSaveRequest {

    @NotBlank(message = "좌석 등급 타입을 입력해주세요.")
    private String seatGrade;

    @PositiveOrZero
    private int price;

    @NotNull
    private Long showId;

    @Builder
    public ShowSeatGradeSaveRequest(String seatGrade, int price, Long showId) {
        this.seatGrade = seatGrade;
        this.price = price;
        this.showId = showId;
    }

    public ShowSeatGrade toEntity() {
        return ShowSeatGrade.builder()
                .seatGrade(seatGrade)
                .price(price)
                .showId(showId)
                .build();
    }
}
