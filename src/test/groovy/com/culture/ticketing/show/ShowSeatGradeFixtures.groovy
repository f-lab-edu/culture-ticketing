package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.domain.ShowSeatGrade

class ShowSeatGradeFixtures {

    static ShowSeatGrade createShowSeatGrade(Long showSeatGradeId, String seatGrade = "VIP", int price = 100000, Long showId = 1L) {
        return ShowSeatGrade.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatGrade(seatGrade)
                .price(price)
                .showId(showId)
                .build();
    }

    static ShowSeatGradeResponse createShowSeatGradeResponse(Long showSeatGradeId, String seatGrade = "VIP", int price = 100000) {
        return ShowSeatGradeResponse.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatGrade(seatGrade)
                .price(price)
                .build();
    }
}
