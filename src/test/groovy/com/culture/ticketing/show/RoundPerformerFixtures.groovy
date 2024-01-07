package com.culture.ticketing.show

import com.culture.ticketing.show.domain.RoundPerformer

class RoundPerformerFixtures {

    static RoundPerformer createRoundPerformer(Long roundPerformerId, Long roundId = 1L, Long performerId = 1L) {
        return RoundPerformer.builder()
                .roundPerformerId(roundPerformerId)
                .roundId(roundId)
                .performerId(performerId)
                .build();
    }
}
