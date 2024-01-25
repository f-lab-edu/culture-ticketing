package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.domain.Round

import java.time.LocalDateTime
import java.util.stream.Collectors

class RoundFixtures {

    static Round createRound(Long roundId,
                             Long showId = 1L,
                             LocalDateTime roundStartDateTime = LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                             LocalDateTime roundEndDateTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0)) {
        return Round.builder()
                .roundId(roundId)
                .showId(showId)
                .roundStartDateTime(roundStartDateTime)
                .roundEndDateTime(roundEndDateTime)
                .build();
    }

    static RoundWithPerformersResponse createRoundWithPerformersResponse(Long roundId, List<Long> performerIds) {
        return RoundWithPerformersResponse.from(createRound(roundId), performerIds.stream()
                .map(performerId -> PerformerFixtures.createPerformer(performerId: performerId))
                .collect(Collectors.toList()))
    }
}
