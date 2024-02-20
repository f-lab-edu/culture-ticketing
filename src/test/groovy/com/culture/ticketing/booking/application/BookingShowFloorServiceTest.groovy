package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowFloorFixtures
import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.application.dto.BookingShowFloorsMapByRoundIdResponse
import com.culture.ticketing.booking.domain.BookingStatus
import com.culture.ticketing.booking.infra.BookingShowFloorRepository
import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorService
import com.culture.ticketing.show.show_floor.domain.ShowFloor
import spock.lang.Specification

class BookingShowFloorServiceTest extends Specification {

    private BookingShowFloorRepository bookingShowFloorRepository = Mock();
    private ShowFloorService showFloorService = Mock();
    private BookingShowFloorService bookingShowFloorService = new BookingShowFloorService(bookingShowFloorRepository, showFloorService);

    def "예약 플로어 목록의 총 가격 합계 구하기"() {

        given:
        List<Long> showFloorIds = [1L, 1L, 2L]
        showFloorService.getTotalPriceByShowFloorIds(showFloorIds) >> 250000

        when:
        int response = bookingShowFloorService.getTotalPriceByShowFloorIds(showFloorIds);

        then:
        response == 250000
    }

    def "예약 플로어 목록 중 해당 회차에 이미 예약된 좌석이 있는지 확인"() {

        given:
        Long roundId = 1L;
        Set<BookingShowFloorSaveRequest> showFloors = [
                new BookingShowFloorSaveRequest(1L, 1),
                new BookingShowFloorSaveRequest(1L, 2),
                new BookingShowFloorSaveRequest(2L, 3)
        ]
        bookingShowFloorRepository.existsAlreadyBookingShowFloorsInRound(showFloors, roundId, BookingStatus.SUCCESS) >> true

        when:
        boolean response = bookingShowFloorService.hasAlreadyBookingShowFloorsByRoundId(roundId, showFloors);

        then:
        response
    }

    def "회차 아이디 목록 내 회차 아이디별 예약된 공연 플로어 목록 조회"() {

        given:
        List<Long> roundIds = [1L, 2L];
        bookingShowFloorRepository.findByBooking_RoundIdInAndBooking_BookingStatus(roundIds, BookingStatus.SUCCESS) >> [
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 1L,
                        showFloorId: 1L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 2L,
                        showFloorId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 2L)
                ),
                BookingShowFloorFixtures.createBookingShowFloor(
                        bookingShowFloorId: 3L,
                        showFloorId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
        ]
        showFloorService.findByIds([1L, 2L, 2L]) >> [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L),
        ]

        when:
        BookingShowFloorsMapByRoundIdResponse response = bookingShowFloorService.findBookingShowFloorsMapByRoundId(roundIds);

        then:
        response.getBookingFloorCountByRoundIdAndShowFloorGradeId(1L, 1L) == 1L
        response.getBookingFloorCountByRoundIdAndShowFloorGradeId(1L, 2L) == 1L
        response.getBookingFloorCountByRoundIdAndShowFloorGradeId(2L, 1L) == 0L
        response.getBookingFloorCountByRoundIdAndShowFloorGradeId(2L, 2L) == 1L
    }
}
