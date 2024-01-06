package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundPerformersSaveRequest;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

        Objects.requireNonNull(request.getRoundId(), "회차 아이디를 입력해주세요.");
        Objects.requireNonNull(request.getPerformerIds(), "출연자 아이디 목록을 입력해주세요.");

        Round round = roundService.findById(request.getRoundId());

        performerService.checkShowPerformersExists(round.getShowId(), request.getPerformerIds());

        roundPerformerRepository.saveAll(request.toEntities());
    }

    @Transactional(readOnly = true)
    public List<RoundPerformer> findByRoundIds(List<Long> roundIds) {

        return roundPerformerRepository.findByRoundIdIn(roundIds);
    }
}
