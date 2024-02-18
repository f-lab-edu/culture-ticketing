package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest
import com.culture.ticketing.booking.domain.BookingStatus
import com.culture.ticketing.booking.infra.BookingShowFloorRepository
import com.culture.ticketing.show.application.ShowFloorService
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
}
