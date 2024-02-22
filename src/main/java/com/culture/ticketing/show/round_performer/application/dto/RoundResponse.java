package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoundResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;

    @Builder
    public RoundResponse(Long roundId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime) {
        this.roundId = roundId;
        this.roundStartDateTime = roundStartDateTime;
        this.roundEndDateTime = roundEndDateTime;
    }

    public static RoundResponse from(Round round) {

        return RoundResponse.builder()
                .roundId(round.getRoundId())
                .roundStartDateTime(round.getRoundStartDateTime())
                .roundEndDateTime(round.getRoundEndDateTime())
                .build();
    }
}
