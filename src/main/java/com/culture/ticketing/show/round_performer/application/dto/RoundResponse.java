package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoundResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;

    public RoundResponse(Round round) {

        this.roundId = round.getRoundId();
        this.roundStartDateTime = round.getRoundStartDateTime();
        this.roundEndDateTime = round.getRoundEndDateTime();
    }
}
