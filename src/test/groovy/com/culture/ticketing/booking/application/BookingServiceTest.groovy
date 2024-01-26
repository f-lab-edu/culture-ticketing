package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.infra.BookingRepository
import com.culture.ticketing.show.application.RoundService
import com.culture.ticketing.show.exception.RoundNotFoundException
import com.culture.ticketing.user.application.UserService
import com.culture.ticketing.user.exception.UserNotFoundException
import spock.lang.Specification

class BookingServiceTest extends Specification {

    private BookingRepository bookingRepository = Mock();
    private UserService userService = Mock();
    private RoundService roundService = Mock();
    private BookingService bookingService = new BookingService(bookingRepository, userService, roundService);

    def "예약 생성 시 해당 유저가 없는 경우 예외 발생"() {

        given:
        Booking booking = BookingFixtures.createBooking(1L);
        userService.notExistsById(1L) >> true

        when:
        bookingService.createBooking(booking);

        then:
        def e = thrown(UserNotFoundException.class)
        e.message == "유저가 존재하지 않습니다. (userId = 1)"
    }

    def "예약 생성 시 해당 회차가 없는 경우 예외 발생"() {

        given:
        Booking booking = BookingFixtures.createBooking(1L);
        roundService.notExistsById(1L) >> true

        when:
        bookingService.createBooking(booking);

        then:
        def e = thrown(RoundNotFoundException.class)
        e.message == "존재하지 않는 회차입니다. (roundId = 1)"
    }

    def "예약 생성 성공"() {

        given:
        Booking booking = BookingFixtures.createBooking(1L);

        when:
        bookingService.createBooking(booking);

        then:
        1 * bookingRepository.save(_)
    }
}
