package com.culture.ticketing.show.show_seat.infra

import com.culture.ticketing.place.AreaFixtures
import com.culture.ticketing.place.SeatFixtures
import com.culture.ticketing.place.domain.Area
import com.culture.ticketing.place.domain.Seat
import com.culture.ticketing.place.infra.AreaRepository
import com.culture.ticketing.place.infra.SeatRepository
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.domain.ShowSeat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShowSeatRepositoryCustomTest extends Specification {

    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AreaRepository areaRepository;

    @BeforeEach
    void setup() {
        showSeatRepository.deleteAll();
        seatRepository.deleteAll();
        areaRepository.deleteAll();
    }

    def "구역 아이디로 공연 좌석 목록 조회"() {

        given:
        List<Area> areas = [
                AreaFixtures.createArea(),
                AreaFixtures.createArea()
        ]
        areaRepository.saveAll(areas);

        List<Seat> seats = [
                SeatFixtures.creatSeat(areaId: areas.get(0).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(0).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(1).areaId),
                SeatFixtures.creatSeat(areaId: areas.get(1).areaId),
        ]
        seatRepository.saveAll(seats);

        List<ShowSeat> showSeats = [
                ShowSeatFixtures.createShowSeat(seatId: seats.get(0).seatId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(1).seatId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(2).seatId),
                ShowSeatFixtures.createShowSeat(seatId: seats.get(3).seatId),
        ]
        showSeatRepository.saveAll(showSeats);

        when:
        List<ShowSeat> response = showSeatRepository.findByAreaId(areas.get(0).areaId);

        then:
        response.size() == 2
        response.showSeatId == [showSeats.get(0).showSeatId, showSeats.get(1).showSeatId]
    }
}
