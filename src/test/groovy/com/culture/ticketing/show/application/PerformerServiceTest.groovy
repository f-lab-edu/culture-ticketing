package com.culture.ticketing.show.application

import com.culture.ticketing.show.PerformerFixtures
import com.culture.ticketing.show.domain.Performer
import com.culture.ticketing.show.exception.ShowPerformerNotMatchException
import com.culture.ticketing.show.infra.PerformerRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class PerformerServiceTest extends Specification {

    @SpringBean
    private PerformerRepository performerRepository = Mock();
    @SpringBean
    private ShowService showService = Mock();
    private PerformerService performerService = new PerformerService(performerRepository, showService);

    def "공연_아이디와_출연자_아이디_목록으로_출연자_목록_조회"() {

        given:
        List<Performer> performers = List.of(
                PerformerFixtures.createPerformer(1L, 1L),
                PerformerFixtures.createPerformer(2L, 1L),
                PerformerFixtures.createPerformer(3L, 2L),
                PerformerFixtures.createPerformer(4L, 1L),
                PerformerFixtures.createPerformer(5L, 3L)
        );
        performerRepository.findByShowIdAndPerformerIdIn(1L, List.of(1L, 2L, 3L)) >> List.of(performers.get(0), performers.get(1))

        when:
        List<Performer> foundPerformers = performerService.findShowPerformers(1L, List.of(1L, 2L, 3L));

        then:
        foundPerformers.collect(performer -> performer.performerId) == [1L, 2L]
    }

    def "출연자_목록_중_해당_공연_출연자가_아닌_경우_예외_발생"() {

        given:
        List<Performer> performers = List.of(
                PerformerFixtures.createPerformer(1L, 1L),
                PerformerFixtures.createPerformer(2L, 1L),
                PerformerFixtures.createPerformer(3L, 2L),
                PerformerFixtures.createPerformer(4L, 1L),
                PerformerFixtures.createPerformer(5L, 3L)
        );
        performerRepository.findByShowIdAndPerformerIdIn(1L, Set.of(1L, 2L, 3L)) >> List.of(performers.get(0), performers.get(1))

        when:
        performerService.checkShowPerformersExists(1L, Set.of(1L, 2L, 3L));

        then:
        def e = thrown(ShowPerformerNotMatchException.class)
        e.message == "해당 공연의 출연자가 아닌 값이 포함되어 있습니다. (performerIds = [3])"
    }
}
