package com.culture.ticketing.show.application

import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaResponse
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest
import com.culture.ticketing.show.exception.ShowAreaGradeNotFoundException
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.infra.ShowAreaRepository
import com.culture.ticketing.show.domain.ShowArea
import spock.lang.Specification

class ShowAreaServiceTest extends Specification {

    private ShowAreaRepository showAreaRepository = Mock();
    private ShowService showService = Mock();
    private ShowAreaGradeService showAreaGradeService = Mock();
    private ShowAreaService showAreaService = new ShowAreaService(showAreaRepository, showService, showAreaGradeService);

    def "공연 구역 생성 성공"() {

        given:
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName("테스트")
                .showId(1L)
                .showAreaGradeId(1L)
                .build();
        showService.notExistsById(1L) >> false
        showAreaGradeService.notExistsById(1L) >> false

        when:
        showAreaService.createShowArea(request);

        then:
        1 * showAreaRepository.save(_) >> { args ->

            def savedArea = args.get(0) as ShowArea

            savedArea.showAreaName == "테스트"
            savedArea.showId == 1L
            savedArea.showAreaGradeId == 1L
        }
    }

    def "공연 구역 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName("테스트")
                .showId(showId)
                .showAreaGradeId(showAreaGradeId)
                .build();

        when:
        showAreaService.createShowArea(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showId | showAreaGradeId || expected
        null   | 1L              || "공연 아이디를 입력해주세요."
        1L     | null            || "공연 구역 등급 아이디를 입력해주세요."
    }

    def "공연 구역 생성 시 요청 값에 적절하지 않는 값이 들어갈 경우 예외 발생"() {

        given:
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName(showAreaName)
                .showId(1L)
                .showAreaGradeId(1L)
                .build();

        when:
        showAreaService.createShowArea(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        showAreaName || expected
        null         || "공연 구역명을 입력해주세요."
        ""           || "공연 구역명을 입력해주세요."
    }

    def "공연 구역 생성 시 공연 아이디가 존재하지 않는 경우 예외 발생"() {

        given:
        Long showId = 1L;
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName("테스트")
                .showId(showId)
                .showAreaGradeId(1L)
                .build();
        showService.notExistsById(showId) >> true

        when:
        showAreaService.createShowArea(request);

        then:
        def e = thrown(ShowNotFoundException.class)
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "공연 구역 생성 시 공연 구역 등급 아이디가 존재하지 않는 경우 예외 발생"() {

        given:
        Long showAreaGradeId = 1L;
        ShowAreaSaveRequest request = ShowAreaSaveRequest.builder()
                .showAreaName("테스트")
                .showId(1L)
                .showAreaGradeId(showAreaGradeId)
                .build();
        showAreaGradeService.notExistsById(showAreaGradeId) >> true

        when:
        showAreaService.createShowArea(request);

        then:
        def e = thrown(ShowAreaGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 구역 등급입니다. (showAreaGradeId = %d)", showAreaGradeId)
    }

    def "공연 구역 아이디 값으로 구역 존재 여부 확인"() {

        given:
        Long showAreaId = 1L;
        showAreaRepository.existsById(showAreaId) >> true;

        when:
        boolean response = showAreaService.notExistsById(showAreaId);

        then:
        !response
    }

    def "공연 아이디로 공연 구역 목록 조회"() {

        given:
        showAreaRepository.findByShowId(1L) >> [
                ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L),
        ]
        showAreaGradeService.findShowAreaGradesByIds([1L, 1L, 2L]) >> [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
        ]

        when:
        List<ShowAreaResponse> response = showAreaService.findShowAreasByShowId(1L);

        then:
        response.size() == 3
        response.showAreaId == [1L, 2L, 3L]
    }

    def "공연 구역 아이디 목록으로 공연 구역 목록 조회"() {

        given:
        showAreaRepository.findAllById([1L, 2L, 3L]) >> [
                ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L),
                ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L),
        ]
        showAreaGradeService.findShowAreaGradesByIds([1L, 1L, 2L]) >> [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L)),
        ]

        when:
        List<ShowAreaResponse> response = showAreaService.findShowAreasByShowAreaIds([1L, 2L, 3L]);

        then:
        response.size() == 3
        response.showAreaId == [1L, 2L, 3L]
    }
}
