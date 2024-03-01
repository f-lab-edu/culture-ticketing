package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundWithPerformersResponse> rounds;
    private final List<ShowAreaGradeResponse> showAreaGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, List<RoundWithPerformersResponse> rounds, ShowAreaGradesResponse showAreaGrades) {
        this.show = show;
        this.rounds = rounds;
        this.showAreaGrades = showAreaGrades.getShowAreaGrades();
    }

    public static ShowDetailResponse from(Show show, Place place, List<RoundWithPerformersResponse> rounds, ShowAreaGradesResponse showAreaGrades) {
        return ShowDetailResponse.builder()
                .show(ShowResponse.from(show, place))
                .rounds(rounds)
                .showAreaGrades(showAreaGrades)
                .build();
    }
}
