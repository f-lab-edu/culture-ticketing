package com.culture.ticketing.show.show_floor.application

import com.culture.ticketing.show.application.ShowService
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.show_floor.ShowFloorGradeFixtures
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeSaveRequest
import com.culture.ticketing.show.show_floor.domain.ShowFloorGrade
import com.culture.ticketing.show.show_floor.infra.ShowFloorGradeRepository
import spock.lang.Specification

class ShowFloorGradeServiceTest extends Specification {

    private ShowFloorGradeRepository showFloorGradeRepository = Mock();
    private ShowService showService = Mock();
    private ShowFloorGradeService showFloorGradeService = new ShowFloorGradeService(showFloorGradeRepository, showService);

    def "공연 플로어 등급 생성 성공"() {

        given:
        ShowFloorGradeSaveRequest request = ShowFloorGradeSaveRequest.builder()
                .showFloorGradeName("VIP")
                .price(200000)
                .showId(1L)
                .build();
        showService.notExistsById(1L) >> false

        when:
        showFloorGradeService.createShowFloorGrade(request);

        then:
        1 * showFloorGradeRepository.save(_) >> { args ->

            def savedShowFloorGrade = args.get(0) as ShowFloorGrade

            savedShowFloorGrade.showFloorGradeName == "VIP"
            savedShowFloorGrade.price == 200000
            savedShowFloorGrade.showId == 1L
        }
    }

    def "공연 플로어 등급 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowFloorGradeSaveRequest request = ShowFloorGradeSaveRequest.builder()
                .showFloorGradeName("VIP")
                .price(200000)
                .showId(null)
                .build();
        showService.notExistsById(1L) >> false

        when:
        showFloorGradeService.createShowFloorGrade(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "공연 플로어 등급 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowFloorGradeSaveRequest request = ShowFloorGradeSaveRequest.builder()
                .showFloorGradeName(showFloorGradeName)
                .price(price)
                .showId(1L)
                .build();
        showService.notExistsById(1L) >> false

        when:
        showFloorGradeService.createShowFloorGrade(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showFloorGradeName | price  || expected
        null               | 200000 || "공연 플로어 등급명을 입력해주세요."
        ""                 | 200000 || "공연 플로어 등급명을 입력해주세요."
        "VIP"              | -1     || "공연 플로어 가격을 0 이상으로 입력해주세요."
    }

    def "공연 플로어 등급 생성 시 공연 아이디 값에 해당하는 공연이 존재하지 않을 경우 예외 발생"() {

        given:
        Long showId = 1L;
        ShowFloorGradeSaveRequest request = ShowFloorGradeSaveRequest.builder()
                .showFloorGradeName("VIP")
                .price(200000)
                .showId(showId)
                .build();
        showService.notExistsById(showId) >> true

        when:
        showFloorGradeService.createShowFloorGrade(request);

        then:
        def e = thrown(ShowNotFoundException.class)
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "아이디 값에 해당하는 공연 플로어 등급이 존재하는지 여부 확인"() {

        given:
        Long showFloorGradeId = 1L;
        showFloorGradeRepository.existsById(showFloorGradeId) >> true

        when:
        boolean response = showFloorGradeService.notExistsById(showFloorGradeId);

        then:
        !response
    }

    def "아이디 목록에 해당하는 공연 플로어 등급 목록 조회"() {

        given:
        List<Long> showFloorGradeIds = [1L, 2L];
        showFloorGradeRepository.findAllById(showFloorGradeIds) >> [
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L),
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L)
        ]

        when:
        List<ShowFloorGrade> response = showFloorGradeService.findByIds(showFloorGradeIds);

        then:
        response.size() == 2
        response.showFloorGradeId == [1L, 2L]
    }

    def "공연 아이디로 공연 플로어 등급 목록 조회"() {

        given:
        showFloorGradeRepository.findByShowId(1L) >> [
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 1L, showId: 1L),
                ShowFloorGradeFixtures.createShowFloorGrade(showFloorGradeId: 2L, showId: 1L)
        ]

        when:
        List<ShowFloorGradeResponse> response = showFloorGradeService.findShowFloorGradesByShowId(1L);

        then:
        response.size() == 2
        response.showFloorGradeId == [1L, 2L]
    }
}
