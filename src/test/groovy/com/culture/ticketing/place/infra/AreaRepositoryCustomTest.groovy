package com.culture.ticketing.place.infra

import com.culture.ticketing.place.AreaFixtures
import com.culture.ticketing.place.SeatFixtures
import com.culture.ticketing.place.domain.Area
import com.culture.ticketing.place.domain.Seat
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.domain.ShowSeat
import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade
import com.culture.ticketing.show.show_seat.infra.ShowSeatGradeRepository
import com.culture.ticketing.show.show_seat.infra.ShowSeatRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AreaRepositoryCustomTest extends Specification {

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private ShowSeatGradeRepository showSeatGradeRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private SeatRepository seatRepository;

    @BeforeEach
    void setup() {
        showSeatRepository.deleteAll();
        showSeatGradeRepository.deleteAll();
        seatRepository.deleteAll();
        areaRepository.deleteAll();
    }

    def "공연 아이디로 좌석이 존재하는 영역 목록 조회"() {

        given:
        List<Area> areas = [
                AreaFixtures.createArea(placeId: 1L),
                AreaFixtures.createArea(placeId: 1L),
                AreaFixtures.createArea(placeId: 1L),
                AreaFixtures.createArea(placeId: 1L)
        ]
        areaRepository.saveAll(areas);

        List<Seat> seats = [
                SeatFixtures.creatSeat(areaId: areas.get(0).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(0).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(0).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(1).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(1).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(2).areaId),
        ]
        seatRepository.saveAll(seats);

        List<ShowSeatGrade> showSeatGrades = [
                ShowSeatGradeFixtures.createShowSeatGrade(showId: 1L),
                ShowSeatGradeFixtures.createShowSeatGrade(showId: 1L),
                ShowSeatGradeFixtures.createShowSeatGrade(showId: 2L),
        ]
        showSeatGradeRepository.saveAll(showSeatGrades);

        List<ShowSeat> showSeats = [
                ShowSeatFixtures.createShowSeat(seatId: seats.get(0).getSeatId(), showSeatGradeId: showSeatGrades.get(0).showSeatGradeId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(1).getSeatId(), showSeatGradeId: showSeatGrades.get(0).showSeatGradeId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(2).getSeatId(), showSeatGradeId: showSeatGrades.get(0).showSeatGradeId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(3).getSeatId(), showSeatGradeId: showSeatGrades.get(1).showSeatGradeId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(4).getSeatId(), showSeatGradeId: showSeatGrades.get(2).showSeatGradeId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(4).getSeatId(), showSeatGradeId: showSeatGrades.get(2).showSeatGradeId),
        ]
        showSeatRepository.saveAll(showSeats);

        when:
        List<Area> response = areaRepository.findByShowId(1L);

        then:
        response.size() == 2
        response.areaId == [areas.get(0).areaId, areas.get(1).areaId]
    }
}
