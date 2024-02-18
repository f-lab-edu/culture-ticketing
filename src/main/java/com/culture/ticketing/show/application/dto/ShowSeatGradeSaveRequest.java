package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeatGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShowSeatGradeSaveRequest {

    private String showSeatGradeName;
    private int price;
    private Long showId;

    @Builder
    public ShowSeatGradeSaveRequest(String showSeatGradeName, int price, Long showId) {
        this.showSeatGradeName = showSeatGradeName;
        this.price = price;
        this.showId = showId;
    }

    public ShowSeatGrade toEntity() {
        return ShowSeatGrade.builder()
                .showSeatGradeName(showSeatGradeName)
                .price(price)
                .showId(showId)
                .build();
    }
}
