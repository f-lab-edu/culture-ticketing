package com.culture.ticketing.show

import com.culture.ticketing.show.domain.Round

import java.time.LocalDateTime

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
}
