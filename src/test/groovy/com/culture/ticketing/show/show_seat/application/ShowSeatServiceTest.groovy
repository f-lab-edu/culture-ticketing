package com.culture.ticketing.show.show_seat.application

import com.culture.ticketing.place.application.SeatService
import com.culture.ticketing.show.show_seat.ShowSeatFixtures
import com.culture.ticketing.show.show_seat.ShowSeatGradeFixtures
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest
import com.culture.ticketing.show.show_seat.domain.ShowSeat
import com.culture.ticketing.show.show_seat.exception.ShowSeatGradeNotFoundException
import com.culture.ticketing.show.show_seat.infra.ShowSeatRepository
import spock.lang.Specification

class ShowSeatServiceTest extends Specification {

    private ShowSeatRepository showSeatRepository = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private SeatService seatService = Mock();
    private ShowSeatService showSeatService = new ShowSeatService(showSeatRepository, showSeatGradeService, seatService);

    def "공연 좌석 정보 생성 성공"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(1L)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();
        showSeatGradeService.notExistsById(1L) >> false

        when:
        showSeatService.createShowSeat(request);

        then:
        1 * showSeatRepository.saveAll(_) >> { args ->

            def savedShowSeats = args.get(0) as List<ShowSeat>

            savedShowSeats.size() == 5
            savedShowSeats.showSeatGradeId == [1L, 1L, 1L, 1L, 1L]
            savedShowSeats.seatId == [1L, 2L, 3L, 4L, 5L]

            return args
        }
    }

    def "공연 좌석 정보 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatIds(seatIds)
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showSeatGradeId | seatIds                    || expected
        null            | Set.of(1L, 2L, 3L, 4L, 5L) || "공연 좌석 등급 아이디를 입력해주세요."
        1L              | null                       || "좌석 아이디를 입력해주세요."
    }

    def "공연 좌석 정보 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatIds(seatIds as Set<Long>)
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showSeatGradeId | seatIds  || expected
        1L              | Set.of() || "좌석 아이디를 입력해주세요."
    }

    def "공연 좌석 정보 생성 시 공연 좌석 등급 아이디 값에 해당하는 공연 좌석 등급이 존재하지 않는 경우 예외 발생"() {

        given:
        Long showSeatGradeId = 1L;
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();
        showSeatGradeService.notExistsById(showSeatGradeId) >> true

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(ShowSeatGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 좌석 등급입니다. (showSeatGradeId = %d)", showSeatGradeId)
    }

    def "공연좌석 아이디 목록으로 총 가격 합계 구하기"() {

        given:
        Set<Long> showSeatIds = [1L, 2L]
        showSeatRepository.findAllById(showSeatIds) >> [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 2L),
        ]
        showSeatGradeService.findByIds([1L, 2L]) >> [
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, price: 200000),
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, price: 150000),
        ]

        when:
        int totalPrice = showSeatService.getTotalPriceByShowSeatIds(showSeatIds);

        then:
        totalPrice == 350000
    }

    def "공연 좌석 아이디 목록으로 공연 좌석 목록 조회"() {

        given:
        List<Long> showSeatIds = [1L, 2L]
        showSeatRepository.findAllById(showSeatIds) >> [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L)
        ]

        when:
        List<ShowSeat> response = showSeatService.findByIds(showSeatIds);

        then:
        response.size() == 2
        response.showSeatId == [1L, 2L]
    }

    def "공연 등급 아이디별 공연 좌석 수 맵핑 값 구하기"() {

        given:
        List<Long> showSeatGradeIds = [1L, 2L];
        showSeatRepository.findByShowSeatGradeIdIn(showSeatGradeIds) >> [
                ShowSeatFixtures.createShowSeat(showSeatId: 1L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 2L, showSeatGradeId: 1L),
                ShowSeatFixtures.createShowSeat(showSeatId: 3L, showSeatGradeId: 2L)
        ]

        when:
        Map<Long, Long> countMapByShowSeatGradeId = showSeatService.countMapByShowSeatGradeId(showSeatGradeIds);

        then:
        countMapByShowSeatGradeId.get(1L) == 2
        countMapByShowSeatGradeId.get(2L) == 1
    }
}
