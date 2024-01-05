package com.culture.ticketing.show

import com.culture.ticketing.show.domain.Performer;

class PerformerFixtures {

    static Performer createPerformer(Long performerId, Long showId) {
        return Performer.builder()
                .performerId(performerId)
                .performerName("사람" + performerId)
                .showId(showId)
                .build();
    }
}
