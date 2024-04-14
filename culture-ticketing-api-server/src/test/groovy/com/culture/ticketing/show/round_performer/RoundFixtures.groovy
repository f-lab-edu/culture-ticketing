package com.culture.ticketing.show.round_performer

import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountResponse
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Round

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
                new RoundResponse(createRound(roundId: map.getOrDefault("roundId", 1L) as Long)),
                (map.getOrDefault("performerIds", []) as List<Long>).stream()
                        .map(performerId -> new PerformerResponse(PerformerFixtures.createPerformer(performerId: performerId)))
                        .collect(Collectors.toList()));

        List<ShowAreaGradeResponse> showAreaGrades = (map.getOrDefault("showAreaGradeIds", []) as List<Long>).stream()
                .map(showAreaGradeId -> new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: showAreaGradeId)))
                .collect(Collectors.toList());
        List<ShowSeatCountResponse> showSeatCountResponses = showAreaGrades.stream()
                .map(showAreaGrade -> new ShowSeatCountResponse(showAreaGrade, 500))
                .collect(Collectors.toList());

        return new RoundWithPerformersAndShowAreaGradesResponse(roundWithPerformersResponse, showSeatCountResponses);
    }
}
