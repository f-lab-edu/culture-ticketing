package com.culture.ticketing.show

import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Show

import java.time.LocalDate

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
}
