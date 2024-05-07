package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingSaveRequest

import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingStatus
import com.culture.ticketing.booking.exception.AlreadyBookingShowSeatsExistsException
import com.culture.ticketing.booking.exception.BookingTotalPriceNotMatchException
import com.culture.ticketing.booking.infra.BookingRepository
import com.culture.ticketing.show.round_performer.application.RoundService
import com.culture.ticketing.show.round_performer.exception.RoundNotFoundException
import com.culture.ticketing.user.application.UserService
import com.culture.ticketing.user.exception.UserNotFoundException
import spock.lang.Specification

class BookingServiceTest extends Specification {

    private BookingRepository bookingRepository = Mock();
    private UserService userService = Mock();
    private RoundService roundService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private BookingService bookingService = new BookingService(bookingRepository, userService, roundService, bookingShowSeatService);

    def "예약 생성 시 요청 값이 null 인 경우 예외 발생"() {

        given:
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(100000)
                .showSeatIds(Set.of(1L, 2L))
                .build();

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        userId | roundId || expected
        null   | 1L      || "유저 아이디를 입력해주세요."
        1L     | null    || "회차 아이디를 입력해주세요."
    }

    def "예약 생성 시 요청 값이 적절하지 않은 경우 예외 발생"() {

        given:
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(totalPrice)
                .showSeatIds(showSeatIds as Set<Long>)
                .build();

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        totalPrice | showSeatIds    || expected
        -1         | Set.of(1L, 2L) || "총 가격은 0 이상 숫자로 입력해주세요."
        100000     | null           || "예약 좌석 정보를 입력해주세요."
        100000     | Set.of()       || "예약 좌석 정보를 입력해주세요."
    }

    def "예약 생성 시 해당 유저가 없는 경우 예외 발생"() {

        given:
        Long userId = 1L;
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(userId)
                .roundId(1L)
                .totalPrice(300000)
                .showSeatIds(Set.of(1L, 2L))
                .build();
        userService.notExistsById(userId) >> true

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(UserNotFoundException.class)
        e.message == String.format("유저가 존재하지 않습니다. (userId = %d)", userId)
    }

    def "예약 생성 시 해당 회차가 없는 경우 예외 발생"() {

        given:
        Long roundId = 1L;
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(roundId)
                .totalPrice(300000)
                .showSeatIds(Set.of(1L, 2L))
                .build();
        roundService.notExistsById(1L) >> true

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(RoundNotFoundException.class)
        e.message == String.format("존재하지 않는 회차입니다. (roundId = %d)", roundId)
    }

    def "예약 생성 시 가격 총 합계가 맞지 않는 경우 예외 발생"() {

        given:
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(250000)
                .showSeatIds(Set.of(1L, 2L))
                .build();
        bookingShowSeatService.getTotalPriceByShowSeatIds(Set.of(1L, 2L)) >> 100000

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(BookingTotalPriceNotMatchException.class)
        e.message == "입력된 예약의 총 금액이 알맞지 않습니다."
    }

    def "예약하려는 좌석 목록 중 이미 예약된 좌석이 있는 경우 예외 발생"() {

        given:
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(300000)
                .showSeatIds(Set.of(1L, 2L))
                .build();
        bookingShowSeatService.getTotalPriceByShowSeatIds(Set.of(1L, 2L)) >> 300000
        bookingShowSeatService.hasAlreadyBookingShowSeatsByRoundId(1L, Set.of(1L, 2L)) >> true

        when:
        bookingService.createBooking(request);

        then:
        def e = thrown(AlreadyBookingShowSeatsExistsException.class)
        e.message == "이미 예약된 좌석입니다."
    }

    def "예약 생성 성공"() {

        given:
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(300000)
                .showSeatIds(Set.of(1L, 2L))
                .build();
        userService.notExistsById(1L) >> false
        roundService.notExistsById(1L) >> false
        bookingShowSeatService.getTotalPriceByShowSeatIds(Set.of(1L, 2L)) >> 300000
        bookingShowSeatService.hasAlreadyBookingShowSeatsByRoundId(1L, Set.of(1L, 2L)) >> false

        when:
        bookingService.createBooking(request);

        then:
        1 * bookingRepository.save(_) >> { args ->

            def savedBooking = args.get(0) as Booking

            savedBooking.bookingId == 1L
            savedBooking.userId == 1L
            savedBooking.roundId == 1L
            savedBooking.bookingStatus == BookingStatus.SUCCESS
            savedBooking.totalPrice == 300000
            savedBooking.bookingShowSeats.showSeatId == [1L, 2L]
        }
    }
}
