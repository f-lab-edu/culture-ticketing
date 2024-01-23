package com.culture.ticketing.show.application

import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest
import com.culture.ticketing.show.RoundPerformerFixtures
import com.culture.ticketing.show.RoundFixtures
import com.culture.ticketing.show.infra.RoundPerformerRepository
import com.culture.ticketing.show.domain.RoundPerformer
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class RoundPerformerServiceTest extends Specification {

    @SpringBean
    private RoundPerformerRepository roundPerformerRepository = Mock();
    @SpringBean
    private PerformerService performerService = Mock();
    @SpringBean
    private RoundService roundService = Mock();
    private RoundPerformerService roundPerformerService = new RoundPerformerService(roundPerformerRepository, performerService, roundService);

    def "회차 출연자 목록 생성 시 회차 아이디가 null 인 경우 예외 발생"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(null)
                .performerIds(Set.of(1L, 2L, 3L))
                .build();

        when:
        roundPerformerService.createRoundPerformers(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "회차 아이디를 입력해주세요."
    }

    def "회차 출연자 목록 생성 시 출연자 아이디 목록이 null 인 경우 예외 발생"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(1L)
                .performerIds(null)
                .build();

        when:
        roundPerformerService.createRoundPerformers(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "출연자 아이디 목록을 입력해주세요."
    }

    def "회차 출연자 목록 생성 성공"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(1L)
                .performerIds(Set.of(1L, 2L, 3L))
                .build();
        roundService.findById(1L) >> RoundFixtures.createRound(1L)

        when:
        roundPerformerService.createRoundPerformers(request);

        then:
        1 * roundPerformerRepository.saveAll(_)
    }

    def "회차_아이디_목록으로_회차_목록_조회"() {

        given:
        List<RoundPerformer> roundPerformers = List.of(
                RoundPerformerFixtures.createRoundPerformer(1L, 1L, 1L),
                RoundPerformerFixtures.createRoundPerformer(2L, 2L, 2L),
                RoundPerformerFixtures.createRoundPerformer(3L, 3L, 1L),
                RoundPerformerFixtures.createRoundPerformer(4L, 3L, 3L),
                RoundPerformerFixtures.createRoundPerformer(5L, 1L, 2L),

        );
        List<Long> roundIds = List.of(1L, 2L);
        roundPerformerRepository.findByRoundIdIn(roundIds) >> List.of(roundPerformers.get(0), roundPerformers.get(1), roundPerformers.get(4))

        when:
        List<RoundPerformer> foundRoundPerformers = roundPerformerService.findByRoundIds(roundIds);

        then:
        foundRoundPerformers.collect(round -> roundIds.contains(round.roundId)).size() == 3
        foundRoundPerformers.collect(round -> round.roundPerformerId) == [1L, 2L, 5L]
    }
}
