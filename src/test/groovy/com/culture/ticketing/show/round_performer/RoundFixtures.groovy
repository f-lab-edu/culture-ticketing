package com.culture.ticketing.show.round_performer

import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.application.dto.ShowAreasResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse
import com.culture.ticketing.show.domain.ShowArea
import com.culture.ticketing.show.domain.ShowAreaGrade
import com.culture.ticketing.show.domain.ShowSeat
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Round
import com.culture.ticketing.show.ShowAreaGradeFixtures

import java.time.LocalDateTime
import java.util.stream.Collectors

class RoundFixtures {

    static Round createRound(Map map = [:]) {
        return Round.builder()
                .roundId(map.getOrDefault("roundId", null) as Long)
                .showId(map.getOrDefault("showId", 1L) as Long)
                .roundStartDateTime(map.getOrDefault("roundStartDateTime", LocalDateTime.of(2024, 1, 1, 10, 0, 0)) as LocalDateTime)
                .roundEndDateTime(map.getOrDefault("roundEndDateTime", LocalDateTime.of(2024, 1, 1, 12, 0, 0)) as LocalDateTime)
                .build();
    }

    static RoundWithPerformersAndShowAreaGradesResponse createRoundWithPerformersAndShowSeatsResponse(Map map = [:]) {

        RoundWithPerformersResponse roundWithPerformersResponse = new RoundWithPerformersResponse(
                createRound(roundId: map.getOrDefault("roundId", 1L) as Long),
                (map.getOrDefault("performerIds", []) as List<Long>).stream()
                        .map(performerId -> PerformerFixtures.createPerformer(performerId: performerId))
                        .collect(Collectors.toList()));

        List<ShowSeat> showSeats = (map.getOrDefault("showSeatIds", []) as List<Long>).stream()
                .map(showSeatId -> ShowSeatFixtures.creatShowSeat(showSeatId: showSeatId))
                .collect(Collectors.toList());
        List<ShowArea> showAreas = (map.getOrDefault("showAreaIds", []) as List<Long>).stream()
                .map(showAreaId -> ShowAreaFixtures.createShowArea(showAreaId: showAreaId))
                .collect(Collectors.toList());
        List<ShowAreaGrade> showAreaGrades = (map.getOrDefault("showAreaGradeIds", []) as List<Long>).stream()
                .map(showAreaGradeId -> ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: showAreaGradeId))
                .collect(Collectors.toList());

        ShowSeatCountsResponse showSeatCountsResponse = new ShowSeatCountsResponse(
                showSeats,
                new ShowAreasResponse(showAreas, new ShowAreaGradesResponse(showAreaGrades)),
                new ShowAreaGradesResponse(showAreaGrades)
        );

        return new RoundWithPerformersAndShowAreaGradesResponse(roundWithPerformersResponse, showSeatCountsResponse);
    }
}
