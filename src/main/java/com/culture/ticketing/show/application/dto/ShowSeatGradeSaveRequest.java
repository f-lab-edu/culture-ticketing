package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowSeatGradeSaveRequest {

    private String seatGrade;
    private int price;
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
