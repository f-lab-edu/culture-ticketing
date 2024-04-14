package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Performer
import com.culture.ticketing.show.round_performer.domain.Round
import com.culture.ticketing.show.round_performer.domain.RoundPerformer

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

class ShowFixtures {

    static Show createShow(Map map = [:]) {
        return Show.builder()
                .showId(map.getOrDefault("showId", null) as Long)
                .category(map.getOrDefault("category", Category.CONCERT) as Category)
                .showName(map.getOrDefault("showName", "테스트") as String)
                .ageRestriction(map.getOrDefault("ageRestriction", AgeRestriction.ALL) as AgeRestriction)
                .runningTime(map.getOrDefault("runningTime", 120) as Integer)
                .posterImgUrl(map.getOrDefault("posterImgUrl", "http://abc.jpg") as String)
                .bookingStartDateTime(map.getOrDefault("bookingStartDateTime", LocalDateTime.of(2023, 12, 25, 20, 0, 0)) as LocalDateTime)
                .bookingEndDateTime(map.getOrDefault("bookingEndDateTime", LocalDateTime.of(2023, 12, 31, 23, 59, 59)) as LocalDateTime)
                .showStartDate(map.getOrDefault("showStartDate", LocalDate.of(2024, 1, 1)) as LocalDate)
                .showEndDate(map.getOrDefault("showEndDate", LocalDate.of(2024, 5, 31)) as LocalDate)
                .placeId(map.getOrDefault("placeId", 1L) as Long)
                .build();
    }

    static ShowDetailResponse createShowDetailResponse(Map map = [:]) {
        return new ShowDetailResponse(
                new ShowResponse(createShow(showId: map.getOrDefault("showId", 1L) as Long),
                        new PlaceResponse(PlaceFixtures.createPlace(placeId: map.getOrDefault("placeId", 1L) as Long))),
                0,
                false,
                (map.getOrDefault("roundIds", []) as List<Long>).stream()
                        .map(roundId -> new RoundWithPerformersResponse(new RoundResponse(RoundFixtures.createRound(
                                roundId: roundId)), (map.getOrDefault("performerIds", []) as List<Long>).stream()
                                .map(performerId -> new PerformerResponse(PerformerFixtures.createPerformer(performerId: performerId)))
                                .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList()),
                (map.getOrDefault("showAreaGradeIds", []) as List<Long>).stream()
                        .map(showAreaGradeId -> ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: showAreaGradeId))
                        .collect(Collectors.toList())
        );
    }
}
