package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundWithPerformersResponse> roundsWithPerformers;
    private final List<ShowAreaGradeResponse> showAreaGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, RoundsWithPerformersResponse roundsWithPerformers, List<ShowAreaGradeResponse> showAreaGrades) {
        this.show = show;
        this.roundsWithPerformers = roundsWithPerformers.getRoundWithPerformers();
        this.showAreaGrades = showAreaGrades;
    }

    public static ShowDetailResponse from(Show show, PlaceResponse place, RoundsWithPerformersResponse roundsWithPerformers, List<ShowAreaGradeResponse> showAreaGrades) {
        return ShowDetailResponse.builder()
                .show(ShowResponse.from(show, place))
                .roundsWithPerformers(roundsWithPerformers)
                .showAreaGrades(showAreaGrades)
                .build();
    }
}
