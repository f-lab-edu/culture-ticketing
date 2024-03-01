package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.application.dto.ShowSeatCountResponse;
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RoundWithPerformersAndShowAreaGradesResponse {

    private final Long roundId;
    private final LocalDateTime roundStartDateTime;
    private final LocalDateTime roundEndDateTime;
    private final List<PerformerResponse> performers;
    private final List<ShowSeatCountResponse> showSeatCounts;

    public RoundWithPerformersAndShowAreaGradesResponse(RoundWithPerformersResponse roundWithPerformers, ShowSeatCountsResponse showSeatCounts) {
        this.roundId = roundWithPerformers.getRoundId();
        this.roundStartDateTime = roundWithPerformers.getRoundStartDateTime();
        this.roundEndDateTime = roundWithPerformers.getRoundEndDateTime();
        this.performers = roundWithPerformers.getPerformers();
        this.showSeatCounts = showSeatCounts.getShowSeatCounts();
    }
}
