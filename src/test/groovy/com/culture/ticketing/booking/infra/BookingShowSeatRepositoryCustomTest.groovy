package com.culture.ticketing.booking.infra

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingShowSeat
import com.culture.ticketing.booking.domain.BookingStatus
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingShowSeatRepositoryCustomTest extends Specification {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingShowSeatRepository bookingShowSeatRepository;

    @BeforeEach
    void setup() {
        bookingShowSeatRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    def "회차 아이디와 공연 좌석 아이디 목록으로 "() {

        given:
        Booking booking = BookingFixtures.createBooking(roundId: 1L, bookingStatus: BookingStatus.SUCCESS);
        bookingRepository.save(booking);
        List<BookingShowSeat> bookingShowSeats = [
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 1L, booking: booking),
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 2L, booking: booking),
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 3L, booking: booking),
        ]
        bookingShowSeatRepository.saveAll(bookingShowSeats);

        when:
        List<BookingShowSeat> response = bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(1L, Set.of(1L, 2L));

        then:
        response.size() == 2
        response.bookingShowSeatId == [bookingShowSeats.get(0).bookingShowSeatId, bookingShowSeats.get(1).bookingShowSeatId]
    }

    def "회차 아이디 목록으로 예약 좌석 목록 조회"() {

        given:
        List<Booking> bookings = [
                BookingFixtures.createBooking(roundId: 1L, bookingStatus: BookingStatus.SUCCESS),
                BookingFixtures.createBooking(roundId: 2L, bookingStatus: BookingStatus.SUCCESS),
                BookingFixtures.createBooking(roundId: 2L, bookingStatus: BookingStatus.CANCELLED),
                BookingFixtures.createBooking(roundId: 3L, bookingStatus: BookingStatus.SUCCESS),
        ]
        bookingRepository.saveAll(bookings);
        List<BookingShowSeat> bookingShowSeats = [
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 1L, booking: bookings.get(0)),
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 1L, booking: bookings.get(1)),
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 1L, booking: bookings.get(2)),
                BookingShowSeatFixtures.createBookingShowSeat(showSeatId: 2L, booking: bookings.get(3)),
        ]
        bookingShowSeatRepository.saveAll(bookingShowSeats);

        when:
        List<BookingShowSeat> response = bookingShowSeatRepository.findSuccessBookingShowSeatsByRoundIdIn([1L, 2L]);

        then:
        response.size() == 2
        response.booking.bookingId == [bookings.get(0).bookingId, bookings.get(1).bookingId]
    }
}
