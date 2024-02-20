package com.culture.ticketing.booking.infra

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowFloorFixtures
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingShowFloor
import com.culture.ticketing.booking.domain.BookingStatus
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingShowFloorRepositoryCustomTest extends Specification {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingShowFloorRepository bookingShowFloorRepository;

    @BeforeEach
    void setup() {
        bookingShowFloorRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    def "예약하려는 예약 플로어 목록 중 해당 회차에 이미 예약됐는지 확인"() {

        given:
        Booking booking = BookingFixtures.createBooking(roundId: 1L, bookingStatus: BookingStatus.SUCCESS);
        bookingRepository.save(booking);
        BookingShowFloor bookingShowFloor = BookingShowFloorFixtures.createBookingShowFloor(showFloorId: 1L, entryOrder: 1, booking: booking);
        bookingShowFloorRepository.save(bookingShowFloor);

        when:
        boolean response = bookingShowFloorRepository.existsAlreadyBookingShowFloorsInRound(showFloors, 1L);

        then:
        response == expected

        where:
        showFloors                                      || expected
        Set.of(new BookingShowFloorSaveRequest(1L, 1))  || true
        Set.of(new BookingShowFloorSaveRequest(1L, 1),
                new BookingShowFloorSaveRequest(1L, 2)) || true
        Set.of(new BookingShowFloorSaveRequest(1L, 2))  || false
    }

    def "회차 아이디 목록으로 예약 폴로어 목록 조회"() {

        given:
        List<Booking> bookings = [
                BookingFixtures.createBooking(roundId: 1L, bookingStatus: BookingStatus.SUCCESS),
                BookingFixtures.createBooking(roundId: 2L, bookingStatus: BookingStatus.SUCCESS),
                BookingFixtures.createBooking(roundId: 2L, bookingStatus: BookingStatus.CANCELLED),
                BookingFixtures.createBooking(roundId: 3L, bookingStatus: BookingStatus.SUCCESS),
        ]
        bookingRepository.saveAll(bookings);
        List<BookingShowFloor> bookingShowFloors = [
                BookingShowFloorFixtures.createBookingShowFloor(showFloorId: 1L, entryOrder: 1, booking: bookings.get(0)),
                BookingShowFloorFixtures.createBookingShowFloor(showFloorId: 1L, entryOrder: 2, booking: bookings.get(1)),
                BookingShowFloorFixtures.createBookingShowFloor(showFloorId: 1L, entryOrder: 2, booking: bookings.get(2)),
                BookingShowFloorFixtures.createBookingShowFloor(showFloorId: 2L, entryOrder: 1, booking: bookings.get(3)),
        ]
        bookingShowFloorRepository.saveAll(bookingShowFloors);

        when:
        List<BookingShowFloor> response = bookingShowFloorRepository.findSuccessBookingShowFloorsByRoundIdIn([1L, 2L]);

        then:
        response.size() == 2
        response.booking.bookingId == [bookings.get(0).bookingId, bookings.get(1).bookingId]
    }
}
