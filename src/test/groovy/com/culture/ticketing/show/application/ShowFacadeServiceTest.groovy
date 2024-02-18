package com.culture.ticketing.show.application

import com.culture.ticketing.booking.application.BookingFacadeService
import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.RoundPerformerFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.round_performer.application.RoundPerformerService
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse
import spock.lang.Specification

class ShowFacadeServiceTest extends Specification {

    private ShowService showService = Mock();
    private RoundPerformerService roundPerformerService = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowFloorGradeService showFloorGradeService = Mock();
    private PlaceService placeService = Mock();
    private BookingFacadeService bookingFacadeService = Mock();
    private ShowFacadeService showFacadeService = new ShowFacadeService(showService, roundPerformerService, showSeatGradeService,
            showFloorGradeService, placeService, bookingFacadeService);

    def "공연 아이디로 공연 상세 조회"() {
        given:
        showService.findShowById(1L) >> ShowFixtures.createShow(showId: 1L)
        placeService.findPlaceById(1L) >> PlaceFixtures.createPlace(placeId: 1L)

        showSeatGradeService.findShowSeatGradesByShowId(1L) >> [
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 3L))
        ];

        roundService.findByShowId(1L) >> [
                RoundFixtures.createRound(roundId: 1L, showId: 1L),
                RoundFixtures.createRound(roundId: 2L, showId: 1L),
                RoundFixtures.createRound(roundId: 4L, showId: 1L)
        ]

        roundPerformerService.findByRoundIds([1L, 2L, 4L]) >> [
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 1L, roundId: 1L, performerId: 1L),
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 2L, roundId: 2L, performerId: 2L),
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 5L, roundId: 1L, performerId: 2L)
        ]

        performerService.findShowPerformers(1L, Set.of(1L, 2L)) >> [
                PerformerFixtures.createPerformer(performerId: 1L, showId: 1L),
                PerformerFixtures.createPerformer(performerId: 2L, showId: 1L)
        ]

        when:
        ShowDetailResponse response = showFacadeService.findShowById(1L);

        then:
        response.show.showId == 1L
        response.show.place.placeId == 1L
        response.showSeatGrades.size() == 3
        response.rounds.size() == 3
        response.rounds.collect(r -> r.roundId) == [1L, 2L, 4L]
        response.rounds.get(0).performers.collect(p -> p.performerId) == [1L, 2L]
        response.rounds.get(1).performers.collect(p -> p.performerId) == [2L]
        response.rounds.get(2).performers.collect(p -> p.performerId) == []
    }
}
