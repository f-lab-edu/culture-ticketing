package com.culture.ticketing.show

import com.culture.ticketing.show.domain.Performer;

class PerformerFixtures {

    static Performer createPerformer(Map map = [:]) {
        return Performer.builder()
                .performerId(map.getOrDefault("performerId", 1L))
                .showId(map.getOrDefault("showId", 1L))
                .performerName(map.getOrDefault("performerName", "홍길동"))
                .performerImgUrl(map.getOrDefault("performerImgUrl", "https://abc.jpg"))
                .role(map.getOrDefault("role", "ABC"))
                .build();
    }
}
