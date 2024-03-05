package com.culture.ticketing.show.round_performer.application

import com.culture.ticketing.show.round_performer.PerformerFixtures
import com.culture.ticketing.show.round_performer.application.dto.PerformersResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest
import com.culture.ticketing.show.round_performer.RoundPerformerFixtures
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse
import com.culture.ticketing.show.round_performer.domain.Round
import com.culture.ticketing.show.round_performer.infra.RoundPerformerRepository
import com.culture.ticketing.show.round_performer.domain.RoundPerformer
import spock.lang.Specification

class RoundPerformerServiceTest extends Specification {

    private RoundPerformerRepository roundPerformerRepository = Mock();
    private PerformerService performerService = Mock();
    private RoundService roundService = Mock();
    private RoundPerformerService roundPerformerService = new RoundPerformerService(roundPerformerRepository, performerService, roundService);

    def "회차 출연자 목록 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(roundId)
                .performerIds(performerIds)
                .build();

        when:
        roundPerformerService.createRoundPerformers(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        roundId | performerIds       || expected
        null    | Set.of(1L, 2L, 3L) || "회차 아이디를 입력해주세요."
        1L      | null               || "출연자 아이디 목록을 입력해주세요."
    }

    def "회차 출연자 목록 생성 성공"() {

        given:
        RoundPerformersSaveRequest request = RoundPerformersSaveRequest.builder()
                .roundId(1L)
                .performerIds(Set.of(1L, 2L, 3L))
                .build();
        roundService.findById(1L) >> RoundFixtures.createRound(roundId: 1L)

        when:
        roundPerformerService.createRoundPerformers(request);

        then:
        1 * roundPerformerRepository.saveAll(_) >> { args ->

            def savedRoundPerformer = args.get(0) as List<RoundPerformer>

            savedRoundPerformer.size() == 3
            savedRoundPerformer.roundId == [1L, 1L, 1L]
            savedRoundPerformer.performerId == [1L, 2L, 3L]

            return args
        }
    }

    def "공연 아이디로 회차 및 출연자 정보 목록 조회"() {

        given:
        Long showId = 1L;
        List<Round> rounds = [
                RoundFixtures.createRound(roundId: 1L),
                RoundFixtures.createRound(roundId: 2L)
        ]
        roundService.findByShowId(showId) >> rounds
        performerService.findPerformersByShowId(showId) >> new PerformersResponse([
                PerformerFixtures.createPerformer(performerId: 1L),
                PerformerFixtures.createPerformer(performerId: 2L)
        ])
        roundPerformerRepository.findByRoundIdIn([1L, 2L]) >> [
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 1L, roundId: 1L, performerId: 1L),
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 2L, roundId: 2L, performerId: 2L),
                RoundPerformerFixtures.createRoundPerformer(roundPerformerId: 5L, roundId: 1L, performerId: 2L),
        ]

        when:
        RoundsWithPerformersResponse foundRoundPerformers = roundPerformerService.findRoundsWitPerformersByShowIdAndRounds(showId, rounds);

        then:
        foundRoundPerformers.getRoundWithPerformers().size() == 2
        foundRoundPerformers.getRoundIds() == [1L, 2L]
        foundRoundPerformers.getRoundWithPerformers().get(0).performers.performerId == [1L, 2L]
        foundRoundPerformers.getRoundWithPerformers().get(1).performers.performerId == [2L]
    }
}
