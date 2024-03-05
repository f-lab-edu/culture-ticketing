package com.culture.ticketing.show

import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformersResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Performer
import com.culture.ticketing.show.round_performer.domain.Round
import com.culture.ticketing.show.round_performer.domain.RoundPerformer

import java.time.LocalDate
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
                .showStartDate(map.getOrDefault("showStartDate", LocalDate.of(2024, 1, 1)) as LocalDate)
                .showEndDate(map.getOrDefault("showEndDate", LocalDate.of(2024, 5, 31)) as LocalDate)
                .placeId(map.getOrDefault("placeId", 1L) as Long)
                .build();
    }

    static ShowResponse createShowResponse(Map map = [:]) {
        return ShowResponse.from(
                createShow(showId: map.getOrDefault("showId", 1L) as Long),
                new PlaceResponse(PlaceFixtures.createPlace(placeId: map.getOrDefault("placeId", 1L) as Long))
        );
    }

    static ShowDetailResponse createShowDetailResponse(Map map = [:]) {
        return ShowDetailResponse.builder()
                .show(createShowResponse(
                        showId: map.getOrDefault("showId", 1L) as Long,
                        placeId: map.getOrDefault("placeId", 1L)
                ))
                .roundsWithPerformers(new RoundsWithPerformersResponse(map.getOrDefault("roundPerformers", []) as List<RoundPerformer>,
                        (map.getOrDefault("rounds", []) as List<Round>),
                        new PerformersResponse((map.getOrDefault("performers", []) as List<Performer>)
                )))
                .showAreaGrades(new ShowAreaGradesResponse((map.getOrDefault("showAreaGradeIds", []) as List<Long>).stream()
                        .map(showAreaGradeId -> ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: showAreaGradeId))
                        .collect(Collectors.toList())))
                .build();
    }
}
