package com.culture.ticketing.show

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show

import java.time.LocalDate
import java.util.stream.Collectors

class ShowFixtures {

    static Show createShow(Long showId, Category category = Category.CONCERT, String showName = "테스트", AgeRestriction ageRestriction = AgeRestriction.ALL, int runningTime = 120, String posterImgUrl = "http://abc.jpg",
                           LocalDate showStartDate = LocalDate.of(2024, 1, 1), LocalDate showEndDate = LocalDate.of(2024, 5, 31), Long placeId = 1L) {
        return Show.builder()
                .showId(showId)
                .category(category)
                .showName(showName)
                .ageRestriction(ageRestriction)
                .runningTime(runningTime)
                .posterImgUrl(posterImgUrl)
                .showStartDate(showStartDate)
                .showEndDate(showEndDate)
                .placeId(placeId)
                .build();
    }

    static ShowResponse createShowResponse(Long showId, Long placeId) {
        return ShowResponse.from(createShow(showId), PlaceFixtures.createPlace(placeId));
    }

    static ShowDetailResponse createShowDetailResponse(Long showId, Long placeId, List<Long> roundIds, List<Long> performerIds, List<Long> showSeatGradeIds) {
        return ShowDetailResponse.builder()
                .show(createShowResponse(showId, placeId))
                .rounds(roundIds.stream()
                        .map(roundId -> RoundFixtures.createRoundWithPerformersResponse(roundId, performerIds))
                        .collect(Collectors.toList()))
                .showSeatGrades(showSeatGradeIds.stream()
                        .map(showSeatGradeId -> ShowSeatGradeFixtures.createShowSeatGradeResponse(showSeatGradeId))
                        .collect(Collectors.toList()))
                .build();
    }
}
