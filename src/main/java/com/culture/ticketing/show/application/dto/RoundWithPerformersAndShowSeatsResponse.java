package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Round;
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

    public RoundWithPerformersAndShowSeatsResponse(Round round, List<PerformerResponse> performers, List<ShowSeatGradeWithCountResponse> showSeatGrades) {
        this.roundId = round.getRoundId();
        this.roundStartDateTime = round.getRoundStartDateTime();
        this.roundEndDateTime = round.getRoundEndDateTime();
        this.performers = performers;
        this.showSeatGrades = showSeatGrades;
    }
}
