package com.culture.ticketing.show.application


import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.dto.ShowAreaGradeSaveRequest
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse
import com.culture.ticketing.show.domain.ShowAreaGrade
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.infra.ShowAreaGradeRepository
import spock.lang.Specification

class ShowAreaGradeServiceTest extends Specification {

    private ShowAreaGradeRepository showAreaGradeRepository = Mock();
    private ShowService showService = Mock();
    private ShowAreaGradeService showAreaGradeService = new ShowAreaGradeService(showAreaGradeRepository, showService);

    def "공연 구역 등급 생성 성공"() {

        given:
        ShowAreaGradeSaveRequest request = ShowAreaGradeSaveRequest.builder()
                .showAreaGradeName("R")
                .price(100000)
                .showId(1L)
                .build();
        showService.notExistsById(1L) >> false

        when:
        showAreaGradeService.createShowAreaGrade(request);

        then:
        1 * showAreaGradeRepository.save(_) >> { args ->

            def savedShowAreaGrade = args.get(0) as ShowAreaGrade

            savedShowAreaGrade.showAreaGradeName == "R"
            savedShowAreaGrade.price == 100000
            savedShowAreaGrade.showId == 1L
        }
    }

    def "공연 구역 등급 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowAreaGradeSaveRequest request = ShowAreaGradeSaveRequest.builder()
                .showAreaGradeName("R")
                .price(100000)
                .showId(null)
                .build();

        when:
        showAreaGradeService.createShowAreaGrade(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "공연 구역 등급 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowAreaGradeSaveRequest request = ShowAreaGradeSaveRequest.builder()
                .showAreaGradeName(showAreaGradeName)
                .price(price)
                .showId(1L)
                .build();

        when:
        showAreaGradeService.createShowAreaGrade(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showAreaGradeName | price  || expected
        null              | 100000 || "공연 구역 등급명을 입력해주세요."
        ""                | 100000 || "공연 구역 등급명을 입력해주세요."
        "R"               | -1     || "공연 구역 등급의 가격은 0 이상으로 입력해주세요."
    }

    def "공연 구역 등급 생성 시 공연 아이디 값에 해당하는 공연이 존재하지 않는 경우 예외 발생"() {

        given:
        Long showId = 1L;
        ShowAreaGradeSaveRequest request = ShowAreaGradeSaveRequest.builder()
                .showAreaGradeName("R")
                .price(100000)
                .showId(showId)
                .build();
        showService.notExistsById(showId) >> true

        when:
        showAreaGradeService.createShowAreaGrade(request);

        then:
        def e = thrown(ShowNotFoundException.class)
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "공연 구역 등급 아이디 값에 해당하는 공연 구역 등급 존재 여부 확인"() {

        given:
        Long showAreaGradeId = 1L;
        showAreaGradeRepository.existsById(showAreaGradeId) >> true

        when:
        boolean response = showAreaGradeService.notExistsById(showAreaGradeId);

        then:
        !response
    }

    def "공연 아이디로 공연 구역 등급 목록 조회"() {

        given:
        showAreaGradeRepository.findByShowId(1L) >> [
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L),
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L),
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 3L)
        ]

        when:
        ShowAreaGradesResponse response = showAreaGradeService.findShowAreaGradesByShowId(1L);

        then:
        response.getShowAreaGrades().size() == 3
        response.getShowAreaGrades().showAreaGradeId == [1L, 2L, 3L]
    }

    def "공연 구역 등급 아이디 목록으로 공연 구역 등급 목록 조회"() {

        given:
        List<Long> showAreaGradeIds = [1L, 2L];
        showAreaGradeRepository.findAllById(showAreaGradeIds) >> [
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L),
                ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)
        ]

        when:
        ShowAreaGradesResponse response = showAreaGradeService.findShowAreaGradesByIds(showAreaGradeIds);

        then:
        response.getShowAreaGrades().size() == 2
        response.getShowAreaGrades().showAreaGradeId == [1L, 2L]
    }
}
