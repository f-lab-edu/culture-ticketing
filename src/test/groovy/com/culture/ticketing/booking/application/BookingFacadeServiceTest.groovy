package com.culture.ticketing.booking.application

import com.culture.ticketing.booking.BookingFixtures
import com.culture.ticketing.booking.BookingShowFloorFixtures
import com.culture.ticketing.booking.BookingShowSeatFixtures
import com.culture.ticketing.booking.application.dto.BookingShowFloorsMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.BookingShowSeatsMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.ShowFloorGradeWithCountMapByRoundIdResponse
import com.culture.ticketing.booking.application.dto.ShowSeatGradeWithCountMapByRoundIdResponse
import com.culture.ticketing.booking.domain.BookingShowFloor
import com.culture.ticketing.booking.domain.BookingShowSeat
import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService
import com.culture.ticketing.show.show_floor.application.ShowFloorService
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeIdResponse
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse
import com.culture.ticketing.show.show_floor.domain.ShowFloor
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService
import com.culture.ticketing.show.show_seat.application.ShowSeatService
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatCountMapByShowSeatGradeIdResponse
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.show_seat.domain.ShowSeat
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
        List<ShowSeat> showSeats = [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 3L, showSeatGradeId: 2L),
                ShowSeatFixtures.createShowSeat(showSeatId: 4L, showSeatGradeId: 2L),
        ]
        showSeatService.countMapByShowSeatGradeId([1L, 2L]) >> new ShowSeatCountMapByShowSeatGradeIdResponse(showSeats)
        List<BookingShowSeat> bookingShowSeats = [
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 1L,
                        showSeatId: 1L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 2L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 2L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 3L,
                        showSeatId: 2L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                ),
                BookingShowSeatFixtures.createBookingShowSeat(
                        bookingShowSeatId: 4L,
                        showSeatId: 4L,
                        booking: BookingFixtures.createBooking(roundId: 1L)
                )
        ]
        bookingShowSeatService.findBookingShowSeatsMapByRoundId([1L, 2L]) >> new BookingShowSeatsMapByRoundIdResponse(bookingShowSeats, showSeats)

        when:
        ShowSeatGradeWithCountMapByRoundIdResponse response = bookingFacadeService.findShowSeatGradeWithAvailableCountMapByRoundId(1L, [1L, 2L]);

        then:
        response.getByRoundId(1L).size() == 2
        response.getByRoundId(1L).availableSeatsCount == [0L, 1L]
        response.getByRoundId(2L).size() == 2
        response.getByRoundId(2L).availableSeatsCount == [1L, 2L]
    }

    def "공연 플로어 등급별 예약 가능한 플로어 수를 회차 별로 맵핑한 값 구하기"() {

        given:
        showFloorGradeService.findShowFloorGradesByShowId(1L) >> [
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L)),
                new ShowFloorGradeResponse(ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L)),
        ]
        List<ShowFloor> showFloors = [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L, count: 500L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L, count: 700L)
        ]
        showFloorService.countMapByShowFloorGradeId([1L, 2L]) >> new ShowFloorCountMapByShowFloorGradeIdResponse(showFloors)
        List<BookingShowFloor> bookingShowFloors = [
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
        bookingShowFloorService.findBookingShowFloorsMapByRoundId([1L, 2L]) >> new BookingShowFloorsMapByRoundIdResponse(bookingShowFloors, showFloors)

        when:
        ShowFloorGradeWithCountMapByRoundIdResponse response = bookingFacadeService.findShowFloorGradeWithAvailableCountMapByRoundId(1L, [1L, 2L]);

        then:
        response.getByRoundId(1L).size() == 2
        response.getByRoundId(1L).availableFloorCount == [499L, 699L]
        response.getByRoundId(2L).size() == 2
        response.getByRoundId(2L).availableFloorCount == [500L, 699L]
    }
}