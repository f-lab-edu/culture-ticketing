package com.culture.ticketing.booking.infra

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowFloorFixtures
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingShowFloor
import com.culture.ticketing.booking.domain.BookingStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingShowFloorRepositoryCustomTest extends Specification {

    @Autowired
    private BookingShowFloorRepository bookingShowFloorRepository;

    def "예약하려는 예약 플로어 목록 중 해당 회차에 이미 예약됐는지 확인"() {

        given:
        Booking booking = BookingFixtures.createBooking(bookingId: 1L, roundId: 1L, bookingStatus: BookingStatus.SUCCESS);
        BookingShowFloor bookingShowFloor = BookingShowFloorFixtures.createBookingShowFloor(bookingShowFloorId: 1L, showFloorId: 1L, entryOrder: 1, booking: booking);
        bookingShowFloorRepository.save(bookingShowFloor);

        when:
        boolean response = bookingShowFloorRepository.existsByShowFloorsInAndBooking_RoundIdAndBooking_BookingStatus(showFloors, 1L, BookingStatus.SUCCESS);

        then:
        response == expected

        where:
        showFloors                                      || expected
        Set.of(new BookingShowFloorSaveRequest(1L, 1))  || true
        Set.of(new BookingShowFloorSaveRequest(1L, 1),
                new BookingShowFloorSaveRequest(1L, 2)) || true
        Set.of(new BookingShowFloorSaveRequest(1L, 2))  || false
    }
}
