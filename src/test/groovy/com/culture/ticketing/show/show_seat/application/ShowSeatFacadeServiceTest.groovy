package com.culture.ticketing.show.show_seat.application

import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.BookingShowSeatService
import com.culture.ticketing.place.SeatFixtures
import com.culture.ticketing.place.application.SeatService
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatResponse
import spock.lang.Specification

class ShowSeatFacadeServiceTest extends Specification {

    private ShowSeatService showSeatService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private SeatService seatService = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowSeatFacadeService showSeatFacadeService = new ShowSeatFacadeService(showSeatService, bookingShowSeatService, seatService, showSeatGradeService);

    def "회차 아이디와 구역 아이디로 공연 좌석 정보 목록 조회"() {

        given:
        showSeatService.findByAreaId(1L) >> [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L, seatId: 1L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L, seatId: 2L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 3L, seatId: 3L, showSeatGradeId: 2L),
                ShowSeatFixtures.createShowSeat(showSeatId: 4L, seatId: 4L, showSeatGradeId: 2L),
        ]

        seatService.findBySeatIds([1L, 2L, 3L, 4L]) >> [
                SeatFixtures.creatSeat(seatId: 1L),
                SeatFixtures.creatSeat(seatId: 2L),
                SeatFixtures.creatSeat(seatId: 3L),
                SeatFixtures.creatSeat(seatId: 4L),
        ]

        showSeatGradeService.findByIds([1L, 1L, 2L, 2L]) >> [
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L),
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L),
        ]

        bookingShowSeatService.findByRoundIdAndShowSeatIds(1L, Set.of(1L, 2L, 3L, 4L)) >> [
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 1L),
                BookingShowSeatFixtures.createBookingShowSeat(bookingShowSeatId: 1L, showSeatId: 3L),
        ]

        when:
        List<ShowSeatResponse> response = showSeatFacadeService.findByRoundIdAndAreaId(1L, 1L);

        then:
        response.size() == 4
        response.showSeatId == [1L, 2L, 3L, 4L]
        response.seatId == [1L, 2L, 3L, 4L]
        response.showSeatGradeId == [1L, 1L, 2L, 2L]
        response.isAvailable == [false, true, false, true]
    }
}
