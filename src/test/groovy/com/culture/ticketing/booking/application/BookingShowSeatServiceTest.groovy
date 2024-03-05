package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.dto.BookingShowSeatsResponse
import com.culture.ticketing.booking.application.dto.RoundsShowSeatCountsResponse
import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.ShowSeatService
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.application.dto.ShowAreasResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountResponse
import com.culture.ticketing.show.application.dto.ShowSeatCountsResponse
import spock.lang.Specification

class BookingShowSeatServiceTest extends Specification {

    private BookingShowSeatRepository bookingShowSeatRepository = Mock();
    private ShowSeatService showSeatService = Mock();
    private BookingShowSeatService bookingShowSeatService = new BookingShowSeatService(bookingShowSeatRepository, showSeatService);

    def "예약 좌석 목록의 총 가격 합계 구하기"() {

        given:
        Set<Long> showSeatIds = [1L, 2L]
        showSeatService.getTotalPriceByShowSeatIds(showSeatIds) >> 300000

        when:
        int response = bookingShowSeatService.getTotalPriceByShowSeatIds(showSeatIds);

        then:
        response == 300000
    }

    def "예약 좌석 목록 중 해당 회차에 이미 예약된 좌석이 있는지 확인"() {

        given:
        Long roundId = 1L;
        Set<Long> showSeatIds = [1L, 2L];
        bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds) >> [
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L)
        ]

        when:
        boolean response = bookingShowSeatService.hasAlreadyBookingShowSeatsByRoundId(roundId, showSeatIds);

        then:
        response
    }

    def "회차 아이디와 공연 좌석 목록 중 예약된 공연 좌석 목록 조회"() {

        given:
        Long roundId = 1L;
        List<Long> showSeatIds = [1L, 2L];
        bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds) >> [
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L),
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 2L, showSeatId: 2L)
        ]

        when:
        BookingShowSeatsResponse response = bookingShowSeatService.findByRoundIdAndShowSeatIds(roundId, showSeatIds);

        then:
        response.bookingShowSeats.size() == 2
        response.bookingShowSeats.bookingShowSeatId == [1L, 2L]
    }

    def "회차 목록 내 회차별 예약된 공연 좌석 목록 조회"() {

        given:
        Long showId = 1L
        showSeatService.findShowSeatCountsByShowId(showId) >> new ShowSeatCountsResponse(
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
        )
        List<Long> roundIds = [1L, 2L];
        bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdIn(roundIds) >> [
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 1L,
                        showSeatId: 1L,
                        booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 2L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(bookingId: 2L, roundId: 2L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 3L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L)
                )
        ]
        showSeatService.findByIds([1L, 2L, 2L]) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 3L),
        ]

        when:
        RoundsShowSeatCountsResponse response = bookingShowSeatService.findRoundsShowSeatCounts(showId, roundIds);

        then:
        response.getShowSeatCountsByRoundId(1L).getShowSeatCounts().showAreaGradeId == [1L, 2L]
        response.getShowSeatCountsByRoundId(1L).getShowSeatCounts().availableSeatCount == [499L, 499L]
        response.getShowSeatCountsByRoundId(2L).getShowSeatCounts().showAreaGradeId == [1L, 2L]
        response.getShowSeatCountsByRoundId(2L).getShowSeatCounts().availableSeatCount == [500L, 499L]

    }
}
