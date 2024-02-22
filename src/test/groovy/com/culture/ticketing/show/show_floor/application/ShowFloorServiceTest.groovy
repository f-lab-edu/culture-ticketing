package com.culture.ticketing.show.show_floor.application

import com.culture.ticketing.show.show_floor.ShowFloorFixtures
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeId
import com.culture.ticketing.show.show_floor.exception.ShowFloorGradeNotFoundException
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest
import com.culture.ticketing.show.show_floor.domain.ShowFloor
import com.culture.ticketing.show.show_floor.infra.ShowFloorRepository
import spock.lang.Specification

class ShowFloorServiceTest extends Specification {

    private ShowFloorRepository showFloorRepository = Mock();
    private ShowFloorGradeService showFloorGradeService = Mock();
    private ShowFloorService showFloorService = new ShowFloorService(showFloorRepository, showFloorGradeService);

    def "공연 플로어 생성 성공"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showFloorGradeId(1L)
                .showFloorName("F1")
                .count(700)
                .build();
        showFloorGradeService.notExistsById(1L) >> false

        when:
        showFloorService.createShowFloor(request);

        then:
        1 * showFloorRepository.save(_) >> { args ->

            def savedShowFloor = args.get(0) as ShowFloor

            savedShowFloor.showFloorGradeId == 1L
            savedShowFloor.showFloorName == "F1"
            savedShowFloor.count == 700
        }
    }

    def "공연 플로어 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showFloorGradeId(null)
                .showFloorName("F1")
                .count(700)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 플로어 등급 아이디를 입력해주세요."
    }

    def "공연 플로어 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showFloorGradeId(1L)
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

    def "공연 플로어 생성 시 공연 플로어 등급 아이디 값에 해당하는 공연 플로어 등급이 존재하지 않을 경우 예외 발생"() {

        given:
        Long showFloorGradeId = 1L;
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showFloorGradeId(showFloorGradeId)
                .showFloorName("F1")
                .count(700)
                .build();
        showFloorGradeService.notExistsById(showFloorGradeId) >> true

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(ShowFloorGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 플로어 등급입니다. (showFloorGradeId = %d)", showFloorGradeId)
    }

    def "공연플로어 아이디 목록으로 총 가격 합계 구하기"() {

        given:
        List<Long> showFloorIds = [1L, 1L, 2L]
        showFloorRepository.findAllById(showFloorIds) >> [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 2L)
        ]
        showFloorGradeService.findByIds([1L, 2L]) >> [
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, price: 100000),
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, price: 50000),
        ]

        when:
        int totalPrice = showFloorService.getTotalPriceByShowFloorIds(showFloorIds);

        then:
        totalPrice == 250000
    }

    def "아이디 목록으로 공연 플로어 목록 조회"() {

        given:
        List<Long> showFloorIds = [1L, 2L]
        showFloorRepository.findAllById(showFloorIds) >> [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L)
        ]

        when:
        List<ShowFloor> response = showFloorService.findByIds(showFloorIds);

        then:
        response.size() == 2
        response.showFloorId == [1L, 2L]
    }

    def "공연 플로어 등급 아이디 별로 공연 플로어 좌석 수 맵핑 구하기"() {

        given:
        List<Long> showFloorGradeIds = [1L, 2L]
        showFloorRepository.findByShowFloorGradeIdIn(showFloorGradeIds) >> [
                ShowFloorFixtures.createShowFloor(showFloorId: 1L, showFloorGradeId: 1L, count: 700),
                ShowFloorFixtures.createShowFloor(showFloorId: 2L, showFloorGradeId: 1L, count: 500),
                ShowFloorFixtures.createShowFloor(showFloorId: 3L, showFloorGradeId: 2L, count: 500)
        ]

        when:
        ShowFloorCountMapByShowFloorGradeId countMapByShowFloorGradeId = showFloorService.countMapByShowFloorGradeId(showFloorGradeIds);

        then:
        countMapByShowFloorGradeId.getShowFloorCountByShowFloorGradeId(1L) == 1200
        countMapByShowFloorGradeId.getShowFloorCountByShowFloorGradeId(2L) == 500
    }
}