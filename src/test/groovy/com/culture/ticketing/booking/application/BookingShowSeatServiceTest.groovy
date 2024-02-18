package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.domain.BookingStatus
import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import com.culture.ticketing.show.show_seat.application.ShowSeatService
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
        bookingShowSeatRepository.existsByShowSeatIdInAndBooking_RoundIdAndBooking_BookingStatus(showSeatIds, roundId, BookingStatus.SUCCESS) >> true

        when:
        boolean response = bookingShowSeatService.hasAlreadyBookingShowSeatsByRoundId(roundId, showSeatIds);

        then:
        response
    }
}
