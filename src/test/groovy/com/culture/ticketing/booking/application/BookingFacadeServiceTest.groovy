package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingSaveRequest
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.domain.Booking
import com.culture.ticketing.booking.domain.BookingStatus
import com.culture.ticketing.booking.exception.BookingTotalPriceNotMatchException
import com.culture.ticketing.show.application.ShowFloorService
import com.culture.ticketing.show.application.ShowSeatGradeService
import com.culture.ticketing.show.application.ShowSeatService
import com.culture.ticketing.show.domain.ShowFloor
import com.culture.ticketing.show.domain.ShowSeat
import com.culture.ticketing.show.domain.ShowSeatGrade
import spock.lang.Specification

class BookingFacadeServiceTest extends Specification {

    private BookingService bookingService = Mock();
    private BookingShowFloorService bookingShowFloorService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private ShowSeatService showSeatService = Mock();
    private ShowFloorService showFloorService = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private BookingFacadeService bookingFacadeService = new BookingFacadeService(bookingService, bookingShowFloorService, bookingShowSeatService, showSeatService, showFloorService, showSeatGradeService);

    def "예약 생성 시 요청 값이 null 인 경우 예외 발생"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131)
        ]
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(userId)
                .roundId(roundId)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

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
                .showSeatIds(showSeatIds)
                .showFloors(showFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        totalPrice | showSeatIds | showFloors                                                                           || expected
        -1         | [1L, 2L]    | [new BookingShowFloorSaveRequest(1L, 100), new BookingShowFloorSaveRequest(1L, 131)] || "총 가격은 0 이상 숫자로 입력해주세요."
        100000     | null        | [new BookingShowFloorSaveRequest(1L, 100), new BookingShowFloorSaveRequest(1L, 131)] || "예약 좌석 정보를 입력해주세요."
        100000     | [1L, 2L]    | null                                                                                 || "예약 좌석 정보를 입력해주세요."
        100000     | []          | []                                                                                   || "예약 좌석 정보를 입력해주세요."
    }

    def "예약 총 금액 일치 여부 확인"() {
        given:
        List<Long> showSeatIds = [1L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100)
        ]

        showSeatService.findByIds(showSeatIds) >> [
                ShowSeat.builder()
                        .showSeatId(1L)
                        .showSeatGradeId(1L)
                        .seatId(1L)
                        .build()
        ]
        showFloorService.findByIds([1L]) >> [
                ShowFloor.builder()
                        .showFloorId(1L)
                        .showFloorName("F1")
                        .count(700)
                        .showSeatGradeId(2L)
                        .build()
        ]
        showSeatGradeService.findByIds([1L, 2L]) >> [
                ShowSeatGrade.builder()
                        .showSeatGradeId(1L)
                        .showId(1L)
                        .seatGrade("R")
                        .price(100000)
                        .build(),
                ShowSeatGrade.builder()
                        .showSeatGradeId(2L)
                        .showId(1L)
                        .seatGrade("VIP")
                        .price(160000)
                        .build()
        ]

        when:
        bookingFacadeService.checkBookingTotalPrice(showSeatIds, bookingShowFloors, 200000);

        then:
        def e = thrown(BookingTotalPriceNotMatchException.class)
        e.message == "입력된 예약의 총 금액이 알맞지 않습니다."
    }

    def "예약 생성 성공"() {

        given:
        List<Long> showSeatIds = [1L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100)
        ]

        showSeatService.findByIds(showSeatIds) >> [
                ShowSeat.builder()
                        .showSeatId(1L)
                        .showSeatGradeId(1L)
                        .seatId(1L)
                        .build()
        ]
        showFloorService.findByIds([1L]) >> [
                ShowFloor.builder()
                        .showFloorId(1L)
                        .showFloorName("F1")
                        .count(700)
                        .showSeatGradeId(2L)
                        .build()
        ]
        showSeatGradeService.findByIds([1L, 2L]) >> [
                ShowSeatGrade.builder()
                        .showSeatGradeId(1L)
                        .showId(1L)
                        .seatGrade("R")
                        .price(100000)
                        .build(),
                ShowSeatGrade.builder()
                        .showSeatGradeId(2L)
                        .showId(1L)
                        .seatGrade("VIP")
                        .price(160000)
                        .build()
        ]

        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(260000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        1 * bookingService.createBooking(_) >> { args ->

            def savedBooking = args.get(0) as Booking

            savedBooking.userId == 1L
            savedBooking.roundId == 1L
            savedBooking.totalPrice == 260000
            savedBooking.bookingStatus == BookingStatus.SUCCESS
        }
        1 * bookingShowSeatService.createBookingShowSeats(_, _) >> { args ->

            def savedShowSeatIds = args.get(0) as List<Long>
            def savedBookingId = args.get(1) as Long

            savedBookingId == 1L
            savedShowSeatIds.size() == 1
            savedShowSeatIds == [1L]

            return args
        }
        1 * bookingShowFloorService.createBookingShowFloors(_, _) >> { args ->

            def savedBookingShowFloor = args.get(0) as List<BookingShowFloorSaveRequest>
            def savedBookingId = args.get(1) as Long

            savedBookingId == 1L
            savedBookingShowFloor.size() == 1
            savedBookingShowFloor.showFloorId == [1L]

            return args
        }
    }


}