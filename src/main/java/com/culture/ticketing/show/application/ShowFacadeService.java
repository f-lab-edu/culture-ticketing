package com.culture.ticketing.show.application;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.application.dto.RoundWithPerformersSaveRequest;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.domain.Show;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShowFacadeService {

    private final ShowService showService;
    private final RoundService roundService;
    private final RoundPerformerService roundPerformerService;
    private final ShowSeatGradeService showSeatGradeService;
    private final PlaceService placeService;
    private final PerformerService performerService;

    public ShowFacadeService(ShowService showService, RoundService roundService, RoundPerformerService roundPerformerService,
                             ShowSeatGradeService showSeatGradeService, PlaceService placeService, PerformerService performerService) {
        this.showService = showService;
        this.roundService = roundService;
        this.roundPerformerService = roundPerformerService;
        this.showSeatGradeService = showSeatGradeService;
        this.placeService = placeService;
        this.performerService = performerService;
    }

    @Transactional(readOnly = true)
    public ShowDetailResponse findShowById(Long showId) {

        Show show = showService.findShowById(showId);
        Place place = placeService.findPlaceById(show.getPlaceId());
        List<RoundWithPerformersResponse> rounds = findRoundsByShowId(showId);
        List<ShowSeatGradeResponse> showSeatGrades = showSeatGradeService.findShowSeatGradesByShowId(showId);

        return ShowDetailResponse.from(show, place, rounds, showSeatGrades);
    }

    @Transactional
    public void createRoundWithPerformers(RoundWithPerformersSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getRoundStartDateTime(), "시작 회차 일시를 입력해주세요.");

        Show show = showService.findShowById(request.getShowId());

        Round round = request.toEntity(show);
        roundService.createRound(show, round);

        roundPerformerService.createRoundPerformers(round.getRoundId(), request.getPerformerIds());
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersResponse> findRoundsByShowId(Long showId) {

        List<Round> rounds = roundService.findByShowId(showId);
        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());

        List<RoundPerformer> roundPerformers = roundPerformerService.findByRoundIds(roundIds);
        List<Long> performerIds = roundPerformers.stream()
                .map(RoundPerformer::getPerformerId)
                .collect(Collectors.toList());

        Map<Long, Performer> performerMapById = performerService.findShowPerformers(showId, performerIds).stream()
                .collect(Collectors.toMap(Performer::getPerformerId, Function.identity()));

        Map<Long, List<Performer>> performersMapByRoundId = roundPerformers.stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId, Collectors.mapping(roundPerformer -> performerMapById.get(roundPerformer.getPerformerId()), Collectors.toList())));

        return rounds.stream()
                .map(round -> RoundWithPerformersResponse.from(round, performersMapByRoundId.getOrDefault(round.getRoundId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }
}
