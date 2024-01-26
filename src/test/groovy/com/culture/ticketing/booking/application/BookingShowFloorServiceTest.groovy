package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.infra.BookingShowFloorRepository
import spock.lang.Specification

class BookingShowFloorServiceTest extends Specification {

    private BookingShowFloorRepository bookingShowFloorRepository = Mock();
    private BookingShowFloorService bookingShowFloorService = new BookingShowFloorService(bookingShowFloorRepository);

    def "공연_플로어_예약_성공"() {

        given:
        List<BookingShowFloorSaveRequest> bookingShowFloors = List.of(
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131),
        );

        when:
        bookingShowFloorService.createBookingShowFloors(bookingShowFloors, 1L);

        then:
        1 * bookingShowFloorRepository.saveAll(_)
    }
}
