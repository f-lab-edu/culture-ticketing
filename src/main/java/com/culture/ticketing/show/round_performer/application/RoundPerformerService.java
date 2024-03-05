package com.culture.ticketing.show.round_performer.application;

import com.culture.ticketing.show.round_performer.application.dto.PerformersResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest;
import com.culture.ticketing.show.round_performer.application.dto.RoundsWithPerformersResponse;
import com.culture.ticketing.show.round_performer.domain.Round;
import com.culture.ticketing.show.round_performer.domain.RoundPerformer;
import com.culture.ticketing.show.round_performer.infra.RoundPerformerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public RoundsWithPerformersResponse findRoundsWitPerformersByShowIdAndRounds(Long showId, List<Round> rounds) {

        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());
        List<RoundPerformer> roundPerformers = roundPerformerRepository.findByRoundIdIn(roundIds);
        PerformersResponse performers = performerService.findPerformersByShowId(showId);

        return new RoundsWithPerformersResponse(roundPerformers, rounds, performers);
    }
}
