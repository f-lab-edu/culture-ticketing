package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoundShowSeatCountsResponse {

    private final Long roundId;
    private final ShowSeatCountsResponse showSeatCounts;

    @Builder
    public RoundShowSeatCountsResponse(Long roundId, ShowSeatCountsResponse showSeatCounts) {
        this.roundId = roundId;
        this.showSeatCounts = showSeatCounts;
    }
}
