package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.domain.BookingShowSeat
import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import spock.lang.Specification

class BookingShowSeatServiceTest extends Specification {

    private BookingShowSeatRepository bookingShowSeatRepository = Mock();
    private BookingShowSeatService bookingShowSeatService = new BookingShowSeatService(bookingShowSeatRepository);

    def "공연 좌석 예약 성공"() {

        when:
        bookingShowSeatService.createBookingShowSeats([1L, 2L, 3L], 1L);

        then:
        1 * bookingShowSeatRepository.saveAll(_) >> { args ->

            def savedBookingShowSeats = args.get(0) as List<BookingShowSeat>

            savedBookingShowSeats.size() == 3
            savedBookingShowSeats.bookingId == [1L, 1L, 1L]
            savedBookingShowSeats.showSeatId == [1L, 2L, 3L]

            return savedBookingShowSeats
        }
    }
}
