package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.domain.Round

import java.time.LocalDateTime
import java.util.stream.Collectors

class RoundFixtures {

    static Round createRound(Map map = [:]) {
        return Round.builder()
                .roundId(map.getOrDefault("roundId", 1L) as Long)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .roundStartDateTime(map.getOrDefault("roundStartDateTime", LocalDateTime.of(2024, 1, 1, 10, 0, 0)) as LocalDateTime)
                .roundEndDateTime(map.getOrDefault("roundEndDateTime", LocalDateTime.of(2024, 1, 1, 12, 0, 0)) as LocalDateTime)
                .build();
    }

    static RoundWithPerformersResponse createRoundWithPerformersResponse(Long roundId, List<Long> performerIds) {
        return RoundWithPerformersResponse.from(createRound(roundId: roundId), performerIds.stream()
                .map(performerId -> PerformerFixtures.createPerformer(performerId: performerId))
                .collect(Collectors.toList()))
    }
}
