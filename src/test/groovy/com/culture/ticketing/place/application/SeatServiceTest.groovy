package com.culture.ticketing.place.application

import com.culture.ticketing.place.SeatFixtures
import com.culture.ticketing.place.application.dto.PlaceSeatSaveRequest
import com.culture.ticketing.place.domain.Seat
import com.culture.ticketing.place.exception.AreaNotFoundException
import com.culture.ticketing.place.exception.DuplicatedPlaceSeatException
import com.culture.ticketing.place.exception.SeatNotFoundException
import com.culture.ticketing.place.infra.SeatRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class SeatServiceTest extends Specification {

    @SpringBean
    private SeatRepository seatRepository = Mock();
    @SpringBean
    private AreaService areaService = Mock();
    private SeatService seatService = new SeatService(seatRepository, areaService);

    def "좌석 생성 성공"() {
        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(1L)
                .build();
        areaService.notExistsById(1L) >> false
        seatRepository.findByAreaIdAndSeatRowAndSeatNumber(request.getAreaId(), request.getSeatRow(), request.getSeatNumber()) >> Optional.empty()

        when:
        seatService.createPlaceSeat(request);

        then:
        1 * seatRepository.save(_) >> { args ->

            def savedSeat = args.get(0) as Seat

            savedSeat.seatRow == 1
            savedSeat.seatNumber == 1
            savedSeat.areaId == 1L
        }
    }

    def "좌석 생성 시 구역 아이디가 null 인 경우 예외 발생"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(null)
                .build();

        when:
        seatService.createPlaceSeat(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "구역 아이디를 입력해주세요."
    }

    def "좌석 생성 시 좌석 행이 0 이하 인 경우 예외 발생"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(0)
                .seatNumber(1)
                .areaId(1L)
                .build();

        when:
        seatService.createPlaceSeat(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 행을 1 이상 숫자로 입력해주세요."
    }

    def "좌석 생성 시 좌석 번호가 0이하 인 경우 예외 발생"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(0)
                .areaId(1L)
                .build();

        when:
        seatService.createPlaceSeat(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 번호를 1 이상 숫자로 입력해주세요."
    }

    def "좌석 생성 시 구역 아이디 값에 해당하는 구역이 존재하지 않는 경우 예외 발생"() {

        given:
        Long areaId = 1L;
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(areaId)
                .build();
        areaService.notExistsById(areaId) >> true

        when:
        seatService.createPlaceSeat(request);

        then:
        def e = thrown(AreaNotFoundException.class)
        e.message == String.format("존재하지 않는 구역입니다. (areaId = %d)", areaId)
    }

    def "좌석 생성 시 이미 동일한 정보의 좌석이 존재하는 경우 예외 발생"() {

        given:
        PlaceSeatSaveRequest request = PlaceSeatSaveRequest.builder()
                .seatRow(1)
                .seatNumber(1)
                .areaId(1L)
                .build();
        seatRepository.findByAreaIdAndSeatRowAndSeatNumber(request.getAreaId(), request.getSeatRow(), request.getSeatNumber()) >> Optional.of(request.toEntity())

        when:
        seatService.createPlaceSeat(request);

        then:
        def e = thrown(DuplicatedPlaceSeatException.class)
        e.message == "해당 장소에 동일한 좌석이 이미 존재합니다."
    }

    def "좌석 아이디 값 목록에 해당하는 값이 없는 경우 예외 발생"() {

        given:
        Set<Long> seatIds = [1L, 2L, 3L, 4L, 5L]
        seatRepository.findBySeatIdIn(seatIds) >> [
                SeatFixtures.creatSeat(seatId: 1L),
                SeatFixtures.creatSeat(seatId: 2L),
                SeatFixtures.creatSeat(seatId: 3L)
        ]

        when:
        seatService.checkSeatsExists(seatIds);

        then:
        def e = thrown(SeatNotFoundException.class)
        e.message == "존재하지 않는 좌석입니다. (seatIds = [4, 5])"
    }
}
