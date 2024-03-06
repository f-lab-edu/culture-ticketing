package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundWithPerformersResponse> roundsWithPerformers;
    private final List<ShowAreaGradeResponse> showAreaGrades;

    public ShowDetailResponse(ShowResponse show, List<RoundWithPerformersResponse> roundsWithPerformers, List<ShowAreaGradeResponse> showAreaGrades) {

        this.show = show;
        this.roundsWithPerformers = roundsWithPerformers;
        this.showAreaGrades = showAreaGrades;
    }
}
