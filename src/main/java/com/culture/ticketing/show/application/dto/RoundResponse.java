package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.domain.Round;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RoundResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;
    private final List<PerformerResponse> performers;

    @Builder
    public RoundResponse(Long roundId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime, List<PerformerResponse> performers) {
        this.roundId = roundId;
        this.roundStartDateTime = roundStartDateTime;
        this.roundEndDateTime = roundEndDateTime;
        this.performers = performers;
    }

    public static RoundResponse from(Round round, List<Performer> performers) {
        return RoundResponse.builder()
                .roundId(round.getRoundId())
                .roundStartDateTime(round.getRoundStartDateTime())
                .roundEndDateTime(round.getRoundEndDateTime())
                .performers(performers.stream()
                        .map(PerformerResponse::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
