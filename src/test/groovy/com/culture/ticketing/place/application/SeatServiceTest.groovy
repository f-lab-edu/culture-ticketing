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

    def "좌석_생성_시_구역_아이디가_null_인_경우_예외_발생"() {

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

    def "좌석_생성_시_좌석_행이_0이하_인_경우_예외_발생"() {

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

    def "좌석_생성_시_좌석_번호가_0이하_인_경우_예외_발생"() {

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

    def "좌석_생성_시_구역_아이디_값에_해당하는_구역이_존재하지_않는_경우_예외_발생"() {

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

    def "좌석_생성_시_이미_동일한_정보의_좌석이_존재하는_경우_예외_발생"() {

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

    def "좌석_아이디_값_목록에_해당하는_값이_없는 경우_예외_발생"() {

        given:
        Set<Long> seatIds = Set.of(1L, 2L, 3L, 4L, 5L);
        List<Seat> foundSeats = List.of(
                SeatFixtures.creatSeat(1L),
                SeatFixtures.creatSeat(2L),
                SeatFixtures.creatSeat(3L)
        )
        seatRepository.findBySeatIdIn(seatIds) >> foundSeats

        when:
        seatService.checkSeatsExists(seatIds);

        then:
        def e = thrown(SeatNotFoundException.class)
        e.message == "존재하지 않는 좌석입니다. (seatIds = [4, 5])"
    }
}
