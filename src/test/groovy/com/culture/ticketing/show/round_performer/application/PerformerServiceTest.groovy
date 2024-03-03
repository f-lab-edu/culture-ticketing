package com.culture.ticketing.show.round_performer.application

import com.culture.ticketing.show.application.ShowService
import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformersResponse
import com.culture.ticketing.show.round_performer.domain.Performer
import com.culture.ticketing.show.round_performer.application.dto.PerformerSaveRequest
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.round_performer.infra.PerformerRepository
import com.culture.ticketing.show.round_performer.exception.ShowPerformerNotMatchException
import spock.lang.Specification

class PerformerServiceTest extends Specification {

    private PerformerRepository performerRepository = Mock();
    private ShowService showService = Mock();
    private PerformerService performerService = new PerformerService(performerRepository, showService);

    def "출연자 생성 성공"() {

        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(1L)
                .performerName("홍길동")
                .build();

        when:
        performerService.createPerformer(request);

        then:
        1 * performerRepository.save(_) >> { args ->

            def savedPerformer = args.get(0) as Performer

            savedPerformer.showId == 1L
            savedPerformer.performerName == "홍길동"
        }
    }

    def "출연자 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(null)
                .performerName("홍길동")
                .build();

        when:
        performerService.createPerformer(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "출연자 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {
        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(1L)
                .performerName(performerName)
                .build();

        when:
        performerService.createPerformer(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == expected

        where:
        performerName || expected
        null          || "출연자 이름을 입력해주세요."
        ""            || "출연자 이름을 입력해주세요."
    }

    def "출연자 생성 시 공연 아이디 값에 해당하는 공연이 존재하지 않는 경우 예외 발생"() {
        given:
        Long showId = 1L;
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(showId)
                .performerName("홍길동")
                .build();
        showService.notExistsById(showId) >> true

        when:
        performerService.createPerformer(request);

        then:
        def e = thrown(ShowNotFoundException.class)
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "공연 아이디로 출연자 목록 조회"() {

        given:
        performerRepository.findByShowId(1L) >> [
                PerformerFixtures.createPerformer(performerId: 1L, showId: 1L),
                PerformerFixtures.createPerformer(performerId: 2L, showId: 1L),
                PerformerFixtures.createPerformer(performerId: 4L, showId: 1L),
        ]

        when:
        PerformersResponse response = performerService.findPerformersByShowId(1L);

        then:
        response.getPerformers().performerId == [1L, 2L, 4L]
    }

    def "출연자 목록 중 해당 공연 출연자가 아닌 경우 예외 발생"() {

        given:
        performerRepository.findByShowIdAndPerformerIdIn(1L, Set.of(1L, 2L, 3L)) >> [
                PerformerFixtures.createPerformer(performerId: 1L, showId: 1L),
                PerformerFixtures.createPerformer(performerId: 2L, showId: 1L)
        ]

        when:
        performerService.checkShowPerformersExists(1L, Set.of(1L, 2L, 3L));

        then:
        def e = thrown(ShowPerformerNotMatchException.class)
        e.message == "해당 공연의 출연자가 아닌 값이 포함되어 있습니다. (performerIds = [3])"
    }
}