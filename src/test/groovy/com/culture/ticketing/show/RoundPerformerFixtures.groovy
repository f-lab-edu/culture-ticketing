package com.culture.ticketing.show

import com.culture.ticketing.show.domain.RoundPerformer

class RoundPerformerFixtures {

    static RoundPerformer createRoundPerformer(Map map = [:]) {
        return RoundPerformer.builder()
                .roundPerformerId(map.getOrDefault("roundPerformerId", 1L) as Long)
                .roundId(map.getOrDefault("roundId", 1L) as Long)
                .performerId(map.getOrDefault("performerId", 1L) as Long)
                .build();
    }
}
