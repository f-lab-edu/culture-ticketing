package com.culture.ticketing.show.round_performer

import com.culture.ticketing.show.round_performer.domain.Performer;

class PerformerFixtures {

    static Performer createPerformer(Map map = [:]) {
        return Performer.builder()
                .performerId(map.getOrDefault("performerId", null) as Long)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .performerName(map.getOrDefault("performerName", "홍길동") as String)
                .performerImgUrl(map.getOrDefault("performerImgUrl", "https://abc.jpg") as String)
                .role(map.getOrDefault("role", "ABC") as String)
                .build();
    }
}
