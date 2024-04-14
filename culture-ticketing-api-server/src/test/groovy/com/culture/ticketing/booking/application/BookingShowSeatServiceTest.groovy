package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.dto.BookingShowSeatResponse
import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import com.culture.ticketing.show.application.ShowSeatService

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
        List<BookingShowSeatResponse> response = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(roundId, showSeatIds);

        then:
        response.size() == 2
        response.bookingShowSeatId == [1L, 2L]
    }

    def "회차 아이디 목록으로 예약된 공연 좌석 목록 조회"() {

        given:
        List<Long> roundIds = [1L, 2L];
        bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdIn(roundIds) >> [
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L)),
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 2L, showSeatId: 2L, booking: BookingFixtures.createBooking(bookingId: 1L, roundId: 1L))
        ]

        when:
        List<BookingShowSeatResponse> response = bookingShowSeatService.findSuccessBookingShowSeatsByRoundIdIn(roundIds);

        then:
        response.size() == 2
        response.bookingShowSeatId == [1L, 2L]
    }
}
