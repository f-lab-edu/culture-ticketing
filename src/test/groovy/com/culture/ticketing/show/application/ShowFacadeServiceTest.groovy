package com.culture.ticketing.show.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowFloorFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.BookingFacadeService
import com.culture.ticketing.booking.application.dto.BookingShowFloorsMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.BookingShowSeatsMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.ShowFloorGradeWithCountMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.ShowSeatGradeWithCountMapByRoundIdResponse
import com.culture.ticketing.booking.domain.BookingShowFloor
import com.culture.ticketing.booking.domain.BookingShowSeat
import com.culture.ticketing.place.AreaFixtures
import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.AreaService
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.application.dto.ShowPlaceAreaResponse
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowSeatsResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeIdResponse
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse
import com.culture.ticketing.show.show_floor.domain.ShowFloor
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.round_performer.application.RoundPerformerService
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatCountMapByShowSeatGradeIdResponse
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.show_seat.domain.ShowSeat
import spock.lang.Specification

import java.time.LocalDate

class ShowFacadeServiceTest extends Specification {

    private ShowService showService = Mock();
    private RoundPerformerService roundPerformerService = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowFloorGradeService showFloorGradeService = Mock();
    private PlaceService placeService = Mock();
    private BookingFacadeService bookingFacadeService = Mock();
    private AreaService areaService = Mock();
    private ShowFacadeService showFacadeService = new ShowFacadeService(showService, roundPerformerService, showSeatGradeService,
            showFloorGradeService, placeService, bookingFacadeService, areaService);

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
        List<Long> roundIds = [1L, 2L];

        List<ShowSeatGradeResponse> showSeatGrades = [
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, showId: 1L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, showId: 1L))
        ]
        List<ShowSeat> showSeats = [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 3L, showSeatGradeId: 2L),
                ShowSeatFixtures.createShowSeat(showSeatId: 4L, showSeatGradeId: 2L),
        ]
        List<BookingShowSeat> bookingShowSeats = [
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 1L,
                        showSeatId: 1L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 2L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 2L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 3L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 4L,
                        showSeatId: 4L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                )
        ]
        bookingFacadeService.findShowSeatGradeWithAvailableCountMapByRoundId(showId, [1L, 2L]) >> new ShowSeatGradeWithCountMapByRoundIdResponse(
                roundIds, showSeatGrades, new ShowSeatCountMapByShowSeatGradeIdResponse(showSeats), new BookingShowSeatsMapByRoundIdResponse(bookingShowSeats, showSeats)
        )

        List<ShowFloorGradeResponse> showFloorGrades = [
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L)),
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L))
        ]
        List<ShowFloor> showFloors = [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L, count: 500L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L, count: 700L),
        ]
        List<BookingShowFloor> bookingShowFloors = [
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 1L,
                        showFloorId: 1L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 2L,
                        showFloorId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 2L)
                ),
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 3L,
                        showFloorId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
        ]
        bookingFacadeService.findShowFloorGradeWithAvailableCountMapByRoundId(showId, [1L, 2L]) >> new ShowFloorGradeWithCountMapByRoundIdResponse(
                roundIds, showFloorGrades, new ShowFloorCountMapByShowFloorGradeIdResponse(showFloors), new BookingShowFloorsMapByRoundIdResponse(bookingShowFloors, showFloors)
        )

        when:
        List<RoundWithPerformersAndShowSeatsResponse> response = showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);

        then:
        response.roundId == [1L, 2L]

        response.find(round -> round.roundId == 1L)
                .performers.performerId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 1L)
                .availableSeatsCount == 0L
        response.find(round -> round.roundId == 1L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 2L)
                .availableSeatsCount == 1L
        response.find(round -> round.roundId == 1L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 1L)
                .availableFloorCount == 499L
        response.find(round -> round.roundId == 1L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 2L)
                .availableFloorCount == 699L

        response.find(round -> round.roundId == 2L)
                .performers.performerId == [1L]
        response.find(round -> round.roundId == 2L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 1L)
                .availableSeatsCount == 1L
        response.find(round -> round.roundId == 2L)
                .showSeatGrades.find(showSeatGrade -> showSeatGrade.showSeatGradeId == 2L)
                .availableSeatsCount == 2L
        response.find(round -> round.roundId == 2L)
                .showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 1L)
                .availableFloorCount == 500L
        response.find(round -> round.roundId == 2L).showFloorGrades.find(showFloorGrade -> showFloorGrade.showFloorGradeId == 2L)
                .availableFloorCount == 699L
    }

    def "공연 아이디로 구역 목록 조회"() {

        given:
        showService.findShowById(1L) >> ShowFixtures.createShow(showId: 1L)
        areaService.findByShowId(1L) >> [
                AreaFixtures.createArea(areaId: 1L),
                AreaFixtures.createArea(areaId: 2L),
                AreaFixtures.createArea(areaId: 3L),
        ]

        when:
        List<ShowPlaceAreaResponse> response = showFacadeService.findAreasByShowId(1L);

        then:
        response.size() == 3
        response.areaId == [1L, 2L, 3L]
    }
}
