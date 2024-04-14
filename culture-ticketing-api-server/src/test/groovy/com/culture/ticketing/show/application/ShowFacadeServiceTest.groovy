package com.culture.ticketing.show.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.BookingShowSeatService
import com.culture.ticketing.booking.application.dto.BookingShowSeatResponse
import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaResponse
import com.culture.ticketing.show.application.dto.ShowResponse

import com.culture.ticketing.show.application.dto.ShowSeatResponse
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.round_performer.application.RoundPerformerService
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.user.UserFixtures
import com.culture.ticketing.user.domain.User
import org.spockframework.spring.SpringBean
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.time.LocalDate

class ShowFacadeServiceTest extends Specification {

    @SpringBean
    private PasswordEncoder passwordEncoder = Mock();
    private ShowService showService = Mock();
    private RoundPerformerService roundPerformerService = Mock();
    private ShowAreaGradeService showAreaGradeService = Mock();
    private ShowAreaService showAreaService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private ShowSeatService showSeatService = Mock();
    private ShowLikeService showLikeService = Mock();
    private ShowFacadeService showFacadeService = new ShowFacadeService(showService, roundPerformerService, showAreaGradeService,
            showAreaService, bookingShowSeatService, showSeatService, showLikeService);

    def "공연 아이디로 공연 상세 조회"() {
        given:
        User user = UserFixtures.createUser(userId: 1L, passwordEncoder);

        showService.findShowById(1L) >> new ShowResponse(ShowFixtures.createShow(showId: 1L), new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)))

        roundPerformerService.findRoundsWitPerformersByShowId(1L) >> [
                new RoundWithPerformersResponse(
                        new RoundResponse(RoundFixtures.createRound(roundId: 1L)),
                        [
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 3L))
                        ]
                ),
                new RoundWithPerformersResponse(
                        new RoundResponse(RoundFixtures.createRound(roundId: 2L)),
                        [
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 3L))
                        ]
                )
        ]

        showAreaGradeService.findShowAreaGradesByShowId(1L) >> [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
        ]

        when:
        ShowDetailResponse response = showFacadeService.findShowById(user, 1L);

        then:
        response.show.showId == 1L
        response.show.place.placeId == 1L
        response.roundsWithPerformers.size() == 2
        response.roundsWithPerformers.roundId == [1L, 2L]
        response.roundsWithPerformers.get(0).performers.size() == 3
        response.roundsWithPerformers.get(0).performers.performerId == [1L, 2L, 3L]
        response.roundsWithPerformers.get(1).performers.size() == 3
        response.roundsWithPerformers.get(1).performers.performerId == [1L, 2L, 3L]
        response.showAreaGrades.size() == 2
        response.showAreaGrades.showAreaGradeId == [1L, 2L]
    }

    def "공연 아이디와 날짜로 회차별 출연자 및 이용 가능한 좌석 수 정보 조회"() {

        given:
        Long showId = 1L;
        LocalDate roundStartDate = LocalDate.of(2024, 1, 1);

        roundPerformerService.findRoundsWithPerformersByShowIdAndRoundStartDate(showId, roundStartDate) >> [
                new RoundWithPerformersResponse(
                        new RoundResponse(RoundFixtures.createRound(roundId: 1L)),
                        [
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 3L))
                        ]
                ),
                new RoundWithPerformersResponse(
                        new RoundResponse(RoundFixtures.createRound(roundId: 2L)),
                        [
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 1L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 2L)),
                                new PerformerResponse(PerformerFixtures.createPerformer(performerId: 3L))
                        ]
                )
        ]

        List<ShowAreaGradeResponse> showAreaGrades = [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
        ]

        showAreaGradeService.findShowAreaGradesByShowId(showId) >> showAreaGrades

        showAreaService.findShowAreasByShowId(showId) >> [
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L), showAreaGrades.get(1)),
        ]

        showSeatService.findByShowAreaIds(Set.of(1L, 2L, 3L)) >> [
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 2L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 3L, showAreaId: 3L))
        ]

        bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdIn(Set.of(1L, 2L)) >> [
                new BookingShowSeatResponse(BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L))),
                new BookingShowSeatResponse(BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 3L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L))),
                new BookingShowSeatResponse(BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 2L, booking: BookingFixtures.createBooking(bookingId: 2L, roundId: 2L))),
        ]

        showSeatService.findByIds(Set.of(1L, 3L, 2L)) >> [
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 2L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 3L, showAreaId: 3L))
        ]

        when:
        List<RoundWithPerformersAndShowAreaGradesResponse> response = showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);

        then:
        response.roundId == [1L, 2L]

        response.find(round -> round.roundId == 1L)
                .performers.performerId == [1L, 2L, 3L]
        response.find(round -> round.roundId == 1L)
                .showSeatCounts.showAreaGradeId == [1L, 2L]
        response.find(round -> round.roundId == 1L)
                .showSeatCounts.availableSeatCount == [1L, 0L]

        response.find(round -> round.roundId == 2L)
                .performers.performerId == [1L, 2L, 3L]
        response.find(round -> round.roundId == 2L)
                .showSeatCounts.showAreaGradeId == [1L, 2L]
        response.find(round -> round.roundId == 2L)
                .showSeatCounts.availableSeatCount == [1L, 1L]
    }

    def "공연 구역 아이디와 회차 이이디로 공연 좌석 목록 조회"() {

        given:
        showSeatService.findShowSeatsByShowAreaId(1L) >> [
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 1L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 2L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 3L)),
                new ShowSeatResponse(ShowSeatFixtures.creatShowSeat(showSeatId: 4L)),
        ]

        bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(1L, Set.of(1L, 2L, 3L, 4L)) >> [
                new BookingShowSeatResponse(BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L)),
                new BookingShowSeatResponse(BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 2L)),
        ]

        when:
        List<ShowSeatResponse> response = showFacadeService.findShowSeatsByShowAreaIdAndRoundId(1L, 1L);

        then:
        response.size() == 4
        response.showSeatId == [1L, 2L, 3L, 4L]
        response.isAvailable == [false, false, true, true]
    }
}
