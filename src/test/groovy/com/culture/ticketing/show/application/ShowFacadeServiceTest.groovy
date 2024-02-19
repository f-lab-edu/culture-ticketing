package com.culture.ticketing.show.application

import com.culture.ticketing.booking.application.BookingFacadeService
import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowSeatsResponse
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

import java.time.LocalDate

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

    def "공연 아이디와 날짜로 회차별 출연자 및 이용 가능한 좌석 수 정보 조회"() {

        given:
        Long showId = 1L;
        LocalDate roundStartDate = LocalDate.of(2024, 1, 1);
        roundPerformerService.findRoundsWitPerformersByShowIdAndRoundStartDate(showId, roundStartDate) >> [
                new RoundWithPerformersResponse(RoundFixtures.createRound(roundId: 1L),
                        [new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)), new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L))]
                ),
                new RoundWithPerformersResponse(RoundFixtures.createRound(roundId: 2L),
                        [new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L))]
                )
        ]
        bookingFacadeService.findShowSeatAvailableCountMapByShowSeatGradeAndRoundId(showId, [1L, 2L]) >>
                Map.of(1L,
                        Map.of(
                                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, showId: 1L)), 100L,
                                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, showId: 1L)), 200L,
                        ) as Map<ShowSeatGradeResponse, Long>,
                        2L,
                        Map.of(
                                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, showId: 1L)), 50L,
                                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, showId: 1L)), 100L,
                        ) as Map<ShowSeatGradeResponse, Long>,
                )
        bookingFacadeService.findShowFloorAvailableCountMapByShowFloorGradeAndRoundId(showId, [1L, 2L]) >>
                Map.of(1L,
                        Map.of(
                                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L)), 100L,
                                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L)), 200L,
                        ) as Map<ShowFloorGradeResponse, Long>,
                        2L,
                        Map.of(
                                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L)), 200L,
                                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L)), 300L,
                        ) as Map<ShowSeatGradeResponse, Long>,
                )

        when:
        List<RoundWithPerformersAndShowSeatsResponse> response = showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);

        then:
        response.roundId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .performers.performerId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 1L)
                .availableSeatsCount == 100L
        response.find(round -> round.roundId == 1L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 2L)
                .availableSeatsCount == 200L
        response.find(round -> round.roundId == 1L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 1L)
                .availableFloorCount == 100L
        response.find(round -> round.roundId == 1L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 2L)
                .availableFloorCount == 200L

        response.find(round -> round.roundId == 2L)
                .performers.performerId == [1L]
        response.find(round -> round.roundId == 2L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 1L)
                .availableSeatsCount == 50L
        response.find(round -> round.roundId == 2L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 2L)
                .availableSeatsCount == 100L
        response.find(round -> round.roundId == 2L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 1L)
                .availableFloorCount == 200L
        response.find(round -> round.roundId == 2L).showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 2L)
                .availableFloorCount == 300L
    }
}
