package com.culture.ticketing.show.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.BookingShowSeatService
import com.culture.ticketing.booking.application.dto.BookingShowSeatsResponse
import com.culture.ticketing.booking.application.dto.RoundsShowSeatCountsResponse
import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.application.dto.ShowAreasResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse
import com.culture.ticketing.show.application.dto.ShowSeatResponse
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.round_performer.RoundPerformerFixtures
import com.culture.ticketing.show.round_performer.application.RoundService
import com.culture.ticketing.show.round_performer.application.dto.PerformersResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.round_performer.application.RoundPerformerService
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Round
import spock.lang.Specification

import java.time.LocalDate

class ShowFacadeServiceTest extends Specification {

    private ShowService showService = Mock();
    private RoundService roundService = Mock();
    private RoundPerformerService roundPerformerService = Mock();
    private ShowAreaGradeService showAreaGradeService = Mock();
    private PlaceService placeService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private ShowSeatService showSeatService = Mock();
    private ShowFacadeService showFacadeService = new ShowFacadeService(showService, roundService,
            roundPerformerService, showAreaGradeService, placeService, bookingShowSeatService, showSeatService);

    def "공연 아이디로 공연 상세 조회"() {
        given:
        showService.findShowById(1L) >> ShowFixtures.createShow(showId: 1L)
        placeService.findPlaceById(1L) >> new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L))
        List<Round> rounds = [
                RoundFixtures.createRound(roundId: 1L),
                RoundFixtures.createRound(roundId: 2L),
        ]
        roundService.findByShowId(1L) >> rounds
        roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(1L, rounds) >> new RoundsWithPerformersResponse(
                [
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 1L, roundId: 1L, performerId: 1L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 2L, roundId: 1L, performerId: 2L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 3L, roundId: 2L, performerId: 1L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 4L, roundId: 2L, performerId: 2L),
                ],
                rounds,
                new PerformersResponse([
                        PerformerFixtures.createPerformer(performerId: 1L),
                        PerformerFixtures.createPerformer(performerId: 2L),
                ])
        )
        showAreaGradeService.findShowAreaGradesByShowId(1L) >> new ShowAreaGradesResponse([
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L),
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L),
        ])

        when:
        ShowDetailResponse response = showFacadeService.findShowById(1L);

        then:
        response.show.showId == 1L
        response.show.place.placeId == 1L
        response.roundsWithPerformers.size() == 2
        response.roundsWithPerformers.roundId == [1L, 2L]
        response.roundsWithPerformers.get(0).performers.performerId == [1L, 2L]
        response.roundsWithPerformers.get(1).performers.performerId == [1L, 2L]
        response.showAreaGrades.size() == 2
    }

    def "공연 아이디와 날짜로 회차별 출연자 및 이용 가능한 좌석 수 정보 조회"() {

        given:
        Long showId = 1L;
        LocalDate roundStartDate = LocalDate.of(2024, 1, 1);
        List<Round> rounds = [
                RoundFixtures.createRound(roundId: 1L),
                RoundFixtures.createRound(roundId: 2L),
        ]
        roundService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate) >> rounds
        roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds) >> new RoundsWithPerformersResponse(
                [
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 1L, roundId: 1L, performerId: 1L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 2L, roundId: 1L, performerId: 2L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 3L, roundId: 2L, performerId: 1L),
                        RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 4L, roundId: 2L, performerId: 2L),
                ],
                rounds,
                new PerformersResponse([
                        PerformerFixtures.createPerformer(performerId: 1L),
                        PerformerFixtures.createPerformer(performerId: 2L),
                ])
        )
        bookingShowSeatService.findRoundsShowSeatCounts(showId, [1L, 2L]) >> new RoundsShowSeatCountsResponse(
                [1L, 2L],
                new ShowSeatCountsResponse(
                        [
                                new ShowSeatCountResponse(
                                        new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                                        500
                                ),
                                new ShowSeatCountResponse(
                                        new ShowAreaGradeResponse(
                                                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)
                                        ),
                                        500
                                ),
                        ],
                        new ShowAreasResponse([
                                ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L),
                                ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L),
                                ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L),
                        ], new ShowAreaGradesResponse([
                                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L),
                                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L),
                        ]))
                ),
                [
                        BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L)),
                        BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 2L, showSeatId: 2L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L)),
                        BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 3L, showSeatId: 2L, booking: BookingFixtures.createBooking(bookingId: 2L, roundId: 2L)),
                ],
                [
                        ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L),
                        ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 3L),
                ]
        )

        when:
        List<RoundWithPerformersAndShowAreaGradesResponse> response = showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);

        then:
        response.roundId == [1L, 2L]

        response.find(round -> round.roundId == 1L)
                .performers.performerId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .showSeatCounts.showAreaGradeId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .showSeatCounts.availableSeatCount == [499L, 499L]

        response.find(round -> round.roundId == 2L)
                .performers.performerId == [1L, 2L]
        response.find(round -> round.roundId == 2L)
                .showSeatCounts.showAreaGradeId == [1L, 2L]
        response.find(round -> round.roundId == 2L)
                .showSeatCounts.availableSeatCount == [500L, 499L]
    }

    def "공연 구역 아이디와 회차 이이디로 공연 좌석 목록 조회"() {

        given:
        showSeatService.findShowSeatsByShowAreaId(1L) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 3L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 4L),
        ]
        bookingShowSeatService.findByRoundIdAndShowSeatIds(1L, [1L, 2L, 3L, 4L]) >> new BookingShowSeatsResponse([
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L),
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 2L),
        ])

        when:
        List<ShowSeatResponse> response = showFacadeService.findShowSeatsByShowAreaIdAndRoundId(1L, 1L);

        then:
        response.size() == 4
        response.showSeatId == [1L, 2L, 3L, 4L]
        response.isAvailable == [false, false, true, true]
    }
}
