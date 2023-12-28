package com.culture.ticketing.show.application;

import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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
    public void createRoundPerformers(Long roundId, Collection<Long> performerIds) {

        Round round = roundService.findById(roundId);

        performerService.checkShowPerformersExists(round.getShowId(), performerIds);

        List<RoundPerformer> roundPerformers = performerIds.stream()
                .map(performerId -> RoundPerformer.builder()
                        .roundId(round.getRoundId())
                        .performerId(performerId)
                        .build())
                .collect(Collectors.toList());

        roundPerformerRepository.saveAll(roundPerformers);
    }

    @Transactional(readOnly = true)
    public List<RoundPerformer> findByRoundIds(List<Long> roundIds) {

        return roundPerformerRepository.findByRoundIdIn(roundIds);
    }
}
