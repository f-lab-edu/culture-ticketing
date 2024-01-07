package com.culture.ticketing.show.application

import com.culture.ticketing.place.PlaceFixtures
import com.culture.ticketing.place.application.PlaceService
import com.culture.ticketing.show.PerformerFixtures
import com.culture.ticketing.show.RoundFixtures
import com.culture.ticketing.show.RoundPerformerFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.ShowSeatGradeFixtures
import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse
import com.culture.ticketing.show.application.dto.ShowDetailResponse
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.domain.Performer
import com.culture.ticketing.show.domain.Round
import com.culture.ticketing.show.domain.RoundPerformer
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ShowFacadeServiceTest extends Specification {

    @SpringBean
    private ShowService showService = Mock();
    @SpringBean
    private RoundService roundService = Mock();
    @SpringBean
    private RoundPerformerService roundPerformerService = Mock();
    @SpringBean
    private ShowSeatGradeService showSeatGradeService = Mock();
    @SpringBean
    private PlaceService placeService = Mock();
    @SpringBean
    private PerformerService performerService = Mock();
    private ShowFacadeService showFacadeService = new ShowFacadeService(showService, roundService, roundPerformerService, showSeatGradeService, placeService, performerService);

    def "공연_아이디로_회차_및_출연자_정보_조회"() {

        given:
        List<Round> rounds = List.of(
                RoundFixtures.createRound(1L, 1L),
                RoundFixtures.createRound(2L, 1L),
                RoundFixtures.createRound(3L, 2L),
                RoundFixtures.createRound(4L, 1L),
                RoundFixtures.createRound(5L, 3L)
        );
        roundService.findByShowId(1L) >> List.of(rounds.get(0), rounds.get(1), rounds.get(3))

        List<RoundPerformer> roundPerformers = List.of(
                RoundPerformerFixtures.createRoundPerformer(1L, 1L, 1L),
                RoundPerformerFixtures.createRoundPerformer(2L, 2L, 2L),
                RoundPerformerFixtures.createRoundPerformer(3L, 3L, 1L),
                RoundPerformerFixtures.createRoundPerformer(4L, 3L, 3L),
                RoundPerformerFixtures.createRoundPerformer(5L, 1L, 2L)

        );
        roundPerformerService.findByRoundIds(List.of(1L, 2L, 4L)) >> List.of(roundPerformers.get(0), roundPerformers.get(1), roundPerformers.get(4))

        List<Performer> performers = List.of(
                PerformerFixtures.createPerformer(1L, 1L),
                PerformerFixtures.createPerformer(2L, 1L),
                PerformerFixtures.createPerformer(3L, 2L),
                PerformerFixtures.createPerformer(4L, 1L),
                PerformerFixtures.createPerformer(5L, 3L)
        );
        performerService.findShowPerformers(1L, Set.of(1L, 2L)) >> List.of(performers.get(0), performers.get(1))

        when:
        List<RoundWithPerformersResponse> response = showFacadeService.findRoundWitPerformersByShowId(1L);

        then:
        response.size() == 3
        response.collect(r -> r.roundId) == [1L, 2L, 4L]
        response.get(0).performers.collect(p -> p.performerId) == [1L, 2L]
        response.get(1).performers.collect(p -> p.performerId) == [2L]
        response.get(2).performers.collect(p -> p.performerId) == []
    }

    def "공연_아이디로_공연_상세_조회"() {
        given:
        showService.findShowById(1L) >> ShowFixtures.createShow(1L)
        placeService.findPlaceById(1L) >> PlaceFixtures.createPlace(1L)

        List<ShowSeatGradeResponse> showSeatGrades = List.of(
                ShowSeatGradeFixtures.createShowSeatGradeResponse(1L),
                ShowSeatGradeFixtures.createShowSeatGradeResponse(2L),
                ShowSeatGradeFixtures.createShowSeatGradeResponse(3L)
        );
        showSeatGradeService.findShowSeatGradesByShowId(1L) >> showSeatGrades;

        List<Round> rounds = List.of(
                RoundFixtures.createRound(1L, 1L),
                RoundFixtures.createRound(2L, 1L),
                RoundFixtures.createRound(3L, 2L),
                RoundFixtures.createRound(4L, 1L),
                RoundFixtures.createRound(5L, 3L)
        );
        roundService.findByShowId(1L) >> List.of(rounds.get(0), rounds.get(1), rounds.get(3))

        List<RoundPerformer> roundPerformers = List.of(
                RoundPerformerFixtures.createRoundPerformer(1L, 1L, 1L),
                RoundPerformerFixtures.createRoundPerformer(2L, 2L, 2L),
                RoundPerformerFixtures.createRoundPerformer(3L, 3L, 1L),
                RoundPerformerFixtures.createRoundPerformer(4L, 3L, 3L),
                RoundPerformerFixtures.createRoundPerformer(5L, 1L, 2L)

        );
        roundPerformerService.findByRoundIds(List.of(1L, 2L, 4L)) >> List.of(roundPerformers.get(0), roundPerformers.get(1), roundPerformers.get(4))

        List<Performer> performers = List.of(
                PerformerFixtures.createPerformer(1L, 1L),
                PerformerFixtures.createPerformer(2L, 1L),
                PerformerFixtures.createPerformer(3L, 2L),
                PerformerFixtures.createPerformer(4L, 1L),
                PerformerFixtures.createPerformer(5L, 3L)
        );
        performerService.findShowPerformers(1L, Set.of(1L, 2L)) >> List.of(performers.get(0), performers.get(1))

        when:
        ShowDetailResponse response = showFacadeService.findShowById(1L);

        then:
        response.show.showId == 1L
        response.show.place.placeId == 1L
        response.showSeatGrades.size() == 3
        response.rounds.size() == 3
        response.rounds.collect(r -> r.roundId) == [1L, 2L, 4L]
        response.rounds.get(0).performers.collect(p -> p.performerId) == [1L, 2L]
        response.rounds.get(1).performers.collect(p -> p.performerId) == [2L]
        response.rounds.get(2).performers.collect(p -> p.performerId) == []
    }
}
