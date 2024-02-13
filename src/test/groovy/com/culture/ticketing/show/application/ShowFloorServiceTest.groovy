package com.culture.ticketing.show.application

import com.culture.ticketing.show.ShowFloorFixtures
import com.culture.ticketing.show.ShowSeatGradeFixtures
import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest
import com.culture.ticketing.show.domain.ShowFloor
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException
import com.culture.ticketing.show.infra.ShowFloorRepository
import spock.lang.Specification

class ShowFloorServiceTest extends Specification {

    private ShowFloorRepository showFloorRepository = Mock();
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowFloorService showFloorService = new ShowFloorService(showFloorRepository, showSeatGradeService);

    def "공연 플로어 생성 성공"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("F1")
                .count(700)
                .build();
        showSeatGradeService.notExistsById(1L) >> false

        when:
        showFloorService.createShowFloor(request);

        then:
        1 * showFloorRepository.save(_) >> { args ->

            def savedShowFloor = args.get(0) as ShowFloor

            savedShowFloor.showSeatGradeId == 1L
            savedShowFloor.showFloorName == "F1"
            savedShowFloor.count == 700
        }
    }

    def "공연 플로어 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(null)
                .showFloorName("F1")
                .count(700)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연 플로어 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName(showFloorName)
                .count(count)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showFloorName | count || expected
        null          | 700   || "공연 플로어 구역명을 입력해주세요."
        ""            | 700   || "공연 플로어 구역명을 입력해주세요."
        "F1"          | 0     || "공연 플로어 인원수를 1 이상 숫자로 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 좌석 등급 아이디 값에 해당하는 공연 좌석 등급이 존재하지 않을 경우 예외 발생"() {

        given:
        Long showSeatGradeId = 1L;
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .showFloorName("F1")
                .count(700)
                .build();
        showSeatGradeService.notExistsById(showSeatGradeId) >> true

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(ShowSeatGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 좌석 등급입니다. (showSeatGradeId = %d)", showSeatGradeId)
    }

    def "공연플로어 아이디 목록으로 총 가격 합계 구하기"() {

        given:
        List<Long> showFloorIds = [1L, 1L, 2L]
        showFloorRepository.findAllById(showFloorIds) >> [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showSeatGradeId: 1L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showSeatGradeId: 2L)
        ]
        showSeatGradeService.findByIds([1L, 2L]) >> [
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 1L, price: 100000),
                ShowSeatGradeFixtures.createShowSeatGrade(showSeatGradeId: 2L, price: 50000),
        ]

        when:
        int totalPrice = showFloorService.getTotalPriceByShowFloorIds(showFloorIds);

        then:
        totalPrice == 250000
    }
}