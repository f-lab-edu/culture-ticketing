package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingSaveRequest
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
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

    def "예약_생성_시_유저_아이디가_null_인_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131)
        ]
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(null)
                .roundId(1L)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "유저 아이디를 입력해주세요."
    }

    def "예약_생성_시_회차_아이디가_null_인_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131),
        ]
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(null)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 아이디를 입력해주세요."
    }

    def "예약_생성_시_가격이_0미만_인_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131),
        ]
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(-1)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "총 가격은 0 이상 숫자로 입력해주세요."
    }

    def "예약_생성_시_예약_좌석_정보가_null_인_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = null;
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100),
                new BookingShowFloorSaveRequest(1L, 131),
        ]
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "예약 좌석 정보를 입력해주세요."
    }

    def "예약_생성_시_예약_플로어_정보가_null_인_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = null
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "예약 좌석 정보를 입력해주세요."
    }

    def "예약_생성_시_예약_좌석_정보와_예약_플로어_정보가_하나도_없는_경우_예외_발생"() {

        given:
        List<Long> showSeatIds = []
        List<BookingShowFloorSaveRequest> bookingShowFloors = []
        BookingSaveRequest request = BookingSaveRequest.builder()
                .userId(1L)
                .roundId(1L)
                .totalPrice(100000)
                .showSeatIds(showSeatIds)
                .showFloors(bookingShowFloors)
                .build();

        when:
        bookingFacadeService.createBooking(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "예약 좌석 정보를 입력해주세요."
    }

    def "예약_총_금액_일치_여부_확인"() {
        given:
        List<Long> showSeatIds = [1L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100)
        ]

        showSeatService.findByIds(showSeatIds) >> List.of(
                ShowSeat.builder()
                        .showSeatId(1L)
                        .showSeatGradeId(1L)
                        .seatId(1L)
                        .build()
        )
        showFloorService.findByIds(List.of(1L)) >> List.of(
                ShowFloor.builder()
                        .showFloorId(1L)
                        .showFloorName("F1")
                        .count(700)
                        .showSeatGradeId(2L)
                        .build()
        )
        showSeatGradeService.findByIds(List.of(1L, 2L)) >> List.of(
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
        )

        when:
        bookingFacadeService.checkBookingTotalPrice(showSeatIds, bookingShowFloors, 200000);

        then:
        def e = thrown(BookingTotalPriceNotMatchException.class)
        e.message == "입력된 예약의 총 금액이 알맞지 않습니다."
    }

    def "예약_생성_성공"() {

        given:
        List<Long> showSeatIds = [1L]
        List<BookingShowFloorSaveRequest> bookingShowFloors = [
                new BookingShowFloorSaveRequest(1L, 100)
        ]

        showSeatService.findByIds(showSeatIds) >> List.of(
                ShowSeat.builder()
                        .showSeatId(1L)
                        .showSeatGradeId(1L)
                        .seatId(1L)
                        .build()
        )
        showFloorService.findByIds(List.of(1L)) >> List.of(
                ShowFloor.builder()
                        .showFloorId(1L)
                        .showFloorName("F1")
                        .count(700)
                        .showSeatGradeId(2L)
                        .build()
        )
        showSeatGradeService.findByIds(List.of(1L, 2L)) >> List.of(
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
        )

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
        1 * bookingService.createBooking(_)
        1 * bookingShowSeatService.createBookingShowSeats(_, _)
        1 * bookingShowFloorService.createBookingShowFloors(_, _)
    }


}
