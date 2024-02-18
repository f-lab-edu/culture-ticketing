package com.culture.ticketing.show.application

import com.culture.ticketing.booking.application.BookingFacadeService
import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse
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

        roundPerformerService.findRoundsWitPerformersByShowId(1L) >> [
                new RoundWithPerformersResponse(RoundFixtures.createRound(roundId: 1L),
                        [new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)), new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L))]
                ),
                new RoundWithPerformersResponse(RoundFixtures.createRound(roundId: 2L),
                        [new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L))]
                )
        ]

        showSeatGradeService.findShowSeatGradesByShowId(1L) >> [
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 3L))
        ];

        showFloorGradeService.findShowFloorGradesByShowId(1L) >> [
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L))
        ]

        when:
        ShowDetailResponse response = showFacadeService.findShowById(1L);

        then:
        response.show.showId == 1L
        response.show.place.placeId == 1L
        response.rounds.size() == 2
        response.rounds.roundId == [1L, 2L]
        response.rounds.get(0).performers.performerId == [1L, 2L]
        response.rounds.get(1).performers.performerId == [1L]
        response.showSeatGrades.size() == 3
        response.showFloorGrades.size() == 1
    }
}
