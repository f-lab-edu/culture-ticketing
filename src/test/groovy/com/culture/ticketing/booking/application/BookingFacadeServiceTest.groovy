package com.culture.ticketing.booking.application

import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_floor.application.ShowFloorService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService
import com.culture.ticketing.show.show_seat.application.ShowSeatService
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse
import spock.lang.Specification

class BookingFacadeServiceTest extends Specification {

    private BookingShowFloorService bookingShowFloorService = Mock();
    private BookingShowSeatService bookingShowSeatService = Mock();
    private ShowSeatService showSeatService = Mock();
    private ShowFloorService showFloorService = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowFloorGradeService showFloorGradeService = Mock();
    private BookingFacadeService bookingFacadeService = new BookingFacadeService(bookingShowFloorService, bookingShowSeatService,
            showSeatService, showFloorService, showSeatGradeService, showFloorGradeService)

    def "공연 좌석 등급별 예약 가능한 좌석 수를 회차 별로 맵핑한 값 구하기"() {

        given:
        showSeatGradeService.findShowSeatGradesByShowId(1L) >> [
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, showId: 1L)),
                new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, showId: 1L)),
        ]
        showSeatService.countMapByShowSeatGradeId([1L, 2L]) >> Map.of(1L, 500L, 2L, 700L);
        bookingShowSeatService.findBookingShowSeatsMapByRoundId([1L, 2L]) >>
                Map.of(1L, [
                        ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                        ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 1L),
                        ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 2L),
                ],
                        2L, [
                        ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                        ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 2L),
                        ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 2L)
                ])

        when:
        Map<Long, Map<ShowSeatGradeResponse, Long>> response = bookingFacadeService.findShowSeatAvailableCountMapByShowSeatGradeAndRoundId(1L, [1L, 2L]);

        then:
        response.keySet().size() == 2
        response.get(1L).keySet().size() == 2
        response.get(1L).get(new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L))) == 498L
        response.get(1L).get(new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L))) == 699L
        response.get(2L).keySet().size() == 2
        response.get(2L).get(new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L))) == 499L
        response.get(2L).get(new ShowSeatGradeResponse(ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L))) == 698L
    }

    def "공연 플로어 등급별 예약 가능한 플로어 수를 회차 별로 맵핑한 값 구하기"() {

        given:
        showFloorGradeService.findShowFloorGradesByShowId(1L) >> [
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L)),
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L)),
        ]
        showFloorService.countMapByShowFloorGradeId([1L, 2L]) >> Map.of(1L, 500L, 2L, 700L)
        bookingShowFloorService.findBookingShowFloorsMapByRoundId([1L, 2L]) >>
                Map.of(1L, [
                        ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L),
                        ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 1L),
                        ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L),
                ],
                2L, [
                        ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L),
                        ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 2L),
                        ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L),
                ])

        when:
        Map<Long, Map<ShowFloorGradeResponse, Long>> response = bookingFacadeService.findShowFloorAvailableCountMapByShowFloorGradeAndRoundId(1L, [1L, 2L]);

        then:
        response.keySet().size() == 2
        response.get(1L).keySet().size() == 2
        response.get(1L).get(new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L))) == 498L
        response.get(1L).get(new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L))) == 699L
        response.get(2L).keySet().size() == 2
        response.get(2L).get(new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L))) == 499L
        response.get(2L).get(new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L))) == 698L
    }
}