package com.culture.ticketing.show.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RoundWithPerformersAndShowSeatsResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;
    private final List<PerformerResponse> performers;
    private final List<ShowSeatGradeWithCountResponse> showSeatGrades;
    private final List<ShowFloorGradeWithCountResponse> showFloorGrades;

    public RoundWithPerformersAndShowSeatsResponse(RoundWithPerformersResponse roundWithPerformers, List<ShowSeatGradeWithCountResponse> showSeatGrades, List<ShowFloorGradeWithCountResponse> showFloorGrades) {
        this.roundId = roundWithPerformers.getRoundId();
        this.roundStartDateTime = roundWithPerformers.getRoundStartDateTime();
        this.roundEndDateTime = roundWithPerformers.getRoundEndDateTime();
        this.performers = roundWithPerformers.getPerformers();
        this.showSeatGrades = showSeatGrades;
        this.showFloorGrades = showFloorGrades;
    }
}
