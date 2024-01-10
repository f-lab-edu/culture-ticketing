package com.culture.ticketing.show.application

import com.culture.ticketing.show.RoundFixtures
import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest
import com.culture.ticketing.show.infra.RoundPerformerRepository
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

    def "회차_출연자_목록_생성_시_회차_아이디가_null_인_경우_예외_발생"() {

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

    def "회차_출연자_목록_생성_시_출연자_아이디_목록이_null_인_경우_예외_발생"() {

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

    def "회차_출연자_목록_생성_성공"() {

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
}
