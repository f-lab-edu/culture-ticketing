package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.Show;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundWithPerformersResponse> rounds;
    private final List<ShowSeatGradeResponse> showSeatGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, List<RoundWithPerformersResponse> rounds, List<ShowSeatGradeResponse> showSeatGrades) {
        this.show = show;
        this.rounds = rounds;
        this.showSeatGrades = showSeatGrades;
    }

    public static ShowDetailResponse from(Show show, Place place, List<RoundWithPerformersResponse> rounds, List<ShowSeatGradeResponse> showSeatGrades) {
        return ShowDetailResponse.builder()
                .show(ShowResponse.from(show, place))
                .rounds(rounds)
                .showSeatGrades(showSeatGrades)
                .build();
    }
}
