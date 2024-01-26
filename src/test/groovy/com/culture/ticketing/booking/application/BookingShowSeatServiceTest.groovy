package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.infra.BookingShowSeatRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class BookingShowSeatServiceTest extends Specification {

    @SpringBean
    private BookingShowSeatRepository bookingShowSeatRepository = Mock();
    private BookingShowSeatService bookingShowSeatService = new BookingShowSeatService(bookingShowSeatRepository);

    def "공연_좌석_예약_성공"() {

        when:
        bookingShowSeatService.createBookingShowSeats(List.of(1L, 2L, 3L), 1L);

        then:
        1 * bookingShowSeatRepository.saveAll(_)
    }
}