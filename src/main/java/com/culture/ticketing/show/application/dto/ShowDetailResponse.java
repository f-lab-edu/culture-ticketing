package com.culture.ticketing.show.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundResponse> rounds;
    private final List<ShowSeatGradeResponse> showSeatGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, List<RoundResponse> rounds, List<ShowSeatGradeResponse> showSeatGrades) {
        this.show = show;
        this.rounds = rounds;
        this.showSeatGrades = showSeatGrades;
    }
}
