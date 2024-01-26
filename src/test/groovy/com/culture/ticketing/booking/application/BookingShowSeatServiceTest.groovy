package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import spock.lang.Specification

class BookingShowSeatServiceTest extends Specification {

    private BookingShowSeatRepository bookingShowSeatRepository = Mock();
    private BookingShowSeatService bookingShowSeatService = new BookingShowSeatService(bookingShowSeatRepository);

    def "공연 좌석 예약 성공"() {

        when:
        bookingShowSeatService.createBookingShowSeats([1L, 2L, 3L], 1L);

        then:
        1 * bookingShowSeatRepository.saveAll(_)
    }
}
