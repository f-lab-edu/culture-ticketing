package com.culture.ticketing.show.round_performer.application;

import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.round_performer.domain.Round;
import com.culture.ticketing.show.round_performer.domain.RoundPerformer;
import com.culture.ticketing.show.round_performer.infra.RoundPerformerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoundPerformerService {

    private final RoundPerformerRepository roundPerformerRepository;
    private final PerformerService performerService;
    private final RoundService roundService;

    public RoundPerformerService(RoundPerformerRepository roundPerformerRepository, PerformerService performerService, RoundService roundService) {
        this.roundPerformerRepository = roundPerformerRepository;
        this.performerService = performerService;
        this.roundService = roundService;
    }

    @Transactional
    public void createRoundPerformers(RoundPerformersSaveRequest request) {

        checkValidRoundPerformersSaveRequest(request);

        Round round = roundService.findById(request.getRoundId());

        performerService.checkShowPerformersExists(round.getShowId(), request.getPerformerIds());

        roundPerformerRepository.saveAll(request.toEntities());
    }

    private void checkValidRoundPerformersSaveRequest(RoundPerformersSaveRequest request) {

        Objects.requireNonNull(request.getRoundId(), "회차 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getPerformerIds(), "출연자 아이디 목록을 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersResponse> findRoundsWitPerformersByShowIdAndRounds(Long showId, List<Round> rounds) {

        List<Long> roundIds = getRoundIds(rounds);
        List<RoundPerformer> roundPerformers = roundPerformerRepository.findByRoundIdIn(roundIds);
        List<PerformerResponse> performers = performerService.findPerformersByShowId(showId);

        Map<Long, List<PerformerResponse>> performersMapByRoundId = getPerformersMapByRoundId(roundPerformers, performers);

        return rounds.stream()
                .map(round -> new RoundWithPerformersResponse(round, performersMapByRoundId.get(round.getRoundId())))
                .collect(Collectors.toList());
    }

    private Map<Long, List<PerformerResponse>> getPerformersMapByRoundId(List<RoundPerformer> roundPerformers, List<PerformerResponse> performers) {

        Map<Long, PerformerResponse> performerMapById = getPerformerMapById(performers);

        return roundPerformers.stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId,
                        Collectors.mapping(roundPerformer -> performerMapById.get(roundPerformer.getPerformerId()), Collectors.toList())));
    }

    private Map<Long, PerformerResponse> getPerformerMapById(List<PerformerResponse> performers) {

        return performers.stream()
                .collect(Collectors.toMap(PerformerResponse::getPerformerId, Function.identity()));
    }

    private List<Long> getRoundIds(List<Round> rounds) {
        return rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());
    }
}
