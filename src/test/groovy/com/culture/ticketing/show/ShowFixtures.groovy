package com.culture.ticketing.show

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures

import java.time.LocalDate
import java.util.stream.Collectors

class ShowFixtures {

    static Show createShow(Map map = [:]) {
        return Show.builder()
                .showId(map.getOrDefault("showId", 1L) as Long)
                .category(map.getOrDefault("category", Category.CONCERT) as Category)
                .showName(map.getOrDefault("showName", "테스트") as String)
                .ageRestriction(map.getOrDefault("ageRestriction", AgeRestriction.ALL) as AgeRestriction)
                .runningTime(map.getOrDefault("runningTime", 120) as Integer)
                .posterImgUrl(map.getOrDefault("posterImgUrl", "http://abc.jpg") as String)
                .showStartDate(map.getOrDefault("showStartDate", LocalDate.of(2024, 1, 1)) as LocalDate)
                .showEndDate(map.getOrDefault("showEndDate", LocalDate.of(2024, 5, 31)) as LocalDate)
                .placeId(map.getOrDefault("placeId", 1L) as Long)
                .build();
    }

    static ShowResponse createShowResponse(Map map = [:]) {
        return ShowResponse.from(
                createShow(showId: map.getOrDefault("showId", 1L) as Long),
                PlaceFixtures.createPlace(placeId: map.getOrDefault("placeId", 1L) as Long)
        );
    }

    static ShowDetailResponse createShowDetailResponse(Map map = [:]) {
        return ShowDetailResponse.builder()
                .show(createShowResponse(
                        showId: map.getOrDefault("showId", 1L) as Long,
                        placeId: map.getOrDefault("placeId", 1L)
                ))
                .rounds((map.getOrDefault("roundIds", []) as List<Long>).stream()
                        .map(roundId -> RoundFixtures.createRoundWithPerformersResponse(
                                roundId: roundId,
                                performerIds: map.getOrDefault("performerIds", []) as List<Long>
                        ))
                        .collect(Collectors.toList()))
                .showSeatGrades((map.getOrDefault("showSeatGradeIds", []) as List<Long>).stream()
                        .map(showSeatGradeId -> ShowSeatGradeFixtures.createShowSeatGradeResponse(showSeatGradeId: showSeatGradeId))
                        .collect(Collectors.toList()))
                .build();
    }
}
