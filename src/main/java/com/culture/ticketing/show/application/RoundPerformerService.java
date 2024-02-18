package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest;
import com.culture.ticketing.show.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
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
    public List<RoundWithPerformersResponse> findRoundsWitPerformersByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        return findRoundsWitPerformersByRounds(showId, roundService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate));
    }

    @Transactional(readOnly = true)
    public List<RoundWithPerformersResponse> findRoundsWitPerformersByShowId(Long showId) {

        return findRoundsWitPerformersByRounds(showId, roundService.findByShowId(showId));
    }

    private List<RoundWithPerformersResponse> findRoundsWitPerformersByRounds(Long showId, List<Round> rounds) {

        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());

        Map<Long, PerformerResponse> performerMapById = performerService.findPerformersByShowId(showId).stream()
                .collect(Collectors.toMap(PerformerResponse::getPerformerId, Function.identity()));

        List<RoundPerformer> roundPerformers = roundPerformerRepository.findByRoundIdIn(roundIds);

        Map<Long, List<PerformerResponse>> performersMapByRoundId = roundPerformers.stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId,
                        Collectors.mapping(roundPerformer -> performerMapById.get(roundPerformer.getPerformerId()), Collectors.toList())));

        return rounds.stream()
                .map(round -> new RoundWithPerformersResponse(
                        round,
                        performersMapByRoundId.getOrDefault(round.getRoundId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }
}
