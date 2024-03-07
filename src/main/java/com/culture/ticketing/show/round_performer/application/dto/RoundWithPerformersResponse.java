package com.culture.ticketing.show.round_performer.application.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RoundWithPerformersResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;
    private final List<PerformerResponse> performers;

    public RoundWithPerformersResponse(RoundResponse round, List<PerformerResponse> performers) {
        this.roundId = round.getRoundId();
        this.roundStartDateTime = round.getRoundStartDateTime();
        this.roundEndDateTime = round.getRoundEndDateTime();
        this.performers = performers;
    }
}
