package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.domain.BookingShowFloor
import com.culture.ticketing.booking.infra.BookingShowFloorRepository
import spock.lang.Specification

class BookingShowFloorServiceTest extends Specification {

    private BookingShowFloorRepository bookingShowFloorRepository = Mock();
    private BookingShowFloorService bookingShowFloorService = new BookingShowFloorService(bookingShowFloorRepository);

    def "공연 플로어 예약 성공"() {

        given:
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131)
        ]

        when:
        bookingShowFloorService.createBookingShowFloors(bookingShowFloors, 1L);

        then:
        1 * bookingShowFloorRepository.saveAll(_) >> { args ->

            def savedBookingShowFloors = args.get(0) as List<BookingShowFloor>

            savedBookingShowFloors.size() == 2
            savedBookingShowFloors.bookingId == [1L, 1L]
            savedBookingShowFloors.showFloorId == [1L, 1L]
            savedBookingShowFloors.entryOrder == [100, 131]

            return savedBookingShowFloors
        }
    }
}
