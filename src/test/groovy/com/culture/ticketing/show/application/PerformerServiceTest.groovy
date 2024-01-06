package com.culture.ticketing.show.application

import com.culture.ticketing.show.PerformerFixtures
import com.culture.ticketing.show.application.dto.PerformerResponse
import com.culture.ticketing.show.application.dto.PerformerSaveRequest
import com.culture.ticketing.show.domain.Performer
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.infra.PerformerRepository
import com.culture.ticketing.show.exception.ShowPerformerNotMatchException
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class PerformerServiceTest extends Specification {

    @SpringBean
    private PerformerRepository performerRepository = Mock();
    @SpringBean
    private ShowService showService = Mock();
    private PerformerService performerService = new PerformerService(performerRepository, showService);

    def "출연자_생성_시_공연_아이디가_null_인_경우_예외_발생"() {

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

    def "출연자_생성_시_출연자_이름이_null_인_경우_예외_발생"() {
        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(1L)
                .performerName(null)
                .build();

        when:
        performerService.createPerformer(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "출연자 이름을 입력해주세요."
    }

    def "출연자_생성_시_출연자_이름이_빈_값인_경우_예외_발생"() {
        given:
        PerformerSaveRequest request = PerformerSaveRequest.builder()
                .showId(1L)
                .performerName("")
                .build();

        when:
        performerService.createPerformer(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "출연자 이름을 입력해주세요."
    }

    def "출연자_생성_시_공연_아이디_값에_해당하는_공연이_존재하지_않는_경우_예외_발생"() {
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

    def "공연별_출연자_목록_조회"() {

        given:
        List<Performer> performers = List.of(
                PerformerFixtures.createPerformer(1L, 1L),
                PerformerFixtures.createPerformer(2L, 1L),
                PerformerFixtures.createPerformer(3L, 2L),
                PerformerFixtures.createPerformer(4L, 1L),
                PerformerFixtures.createPerformer(5L, 2L)
        );
        performerRepository.findByShowId(1L) >> List.of(performers.get(0), performers.get(1), performers.get(3))

        when:
        List<PerformerResponse> response = performerService.findPerformersByShowId(1L);

        then:
        response.collect(performer -> performer.performerId) == [1L, 2L, 4L]
    }

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