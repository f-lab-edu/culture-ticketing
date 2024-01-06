package com.culture.ticketing.show

import com.culture.ticketing.show.domain.Performer;

class PerformerFixtures {

    static Performer createPerformer(Long performerId, Long showId = 1L, String performerName = "홍길동", String performerImgUrl = "https://abc.jpg", String role = "ABC") {
        return Performer.builder()
                .performerId(performerId)
                .showId(showId)
                .performerName(performerName)
                .performerImgUrl(performerImgUrl)
                .role(role)
                .build();
    }
}
