package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTime;
import com.culture.ticketing.show.exception.RoundNotFoundException;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import com.culture.ticketing.show.infra.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundPerformerRepository roundPerformerRepository;
    private final PerformerService performerService;

    public RoundService(RoundRepository roundRepository, RoundPerformerRepository roundPerformerRepository, PerformerService performerService) {
        this.roundRepository = roundRepository;
        this.roundPerformerRepository = roundPerformerRepository;
        this.performerService = performerService;
    }

    @Transactional(readOnly = true)
    public Round findById(Long roundId) {
        return roundRepository.findById(roundId).orElseThrow(() -> {
            throw new RoundNotFoundException(roundId);
        });
    }

    @Transactional
    public void createRound(Show show, Round round) {

        checkOutOfRangeRoundDateTime(round, show);
        checkDuplicatedRoundDateTime(round);

        roundRepository.save(round);
    }

    private void checkDuplicatedRoundDateTime(Round round) {
        roundRepository.findByShowIdAndDuplicatedRoundDateTime(
                round.getShowId(),
                round.getRoundStartDateTime(),
                round.getRoundEndDateTime()).ifPresent(schedule -> {
            throw new DuplicatedRoundDateTimeException();
        });
    }

    private void checkOutOfRangeRoundDateTime(Round round, Show show) {
        LocalDateTime showStartDateTime = LocalDateTime.of(show.getShowStartDate(), LocalTime.MIN);
        LocalDateTime showEndDateTime = LocalDateTime.of(show.getShowEndDate(), LocalTime.MAX);
        if (round.getRoundStartDateTime().isBefore(showStartDateTime)
                || round.getRoundEndDateTime().isAfter(showEndDateTime)) {
            throw new OutOfRangeRoundDateTime();
        }
    }

    @Transactional(readOnly = true)
    public List<RoundResponse> findRoundsByShowId(Long showId) {

        List<Round> rounds = roundRepository.findByShowId(showId);
        List<Long> roundIds = rounds.stream()
                .map(Round::getRoundId)
                .collect(Collectors.toList());

        List<RoundPerformer> roundPerformers = roundPerformerRepository.findByRoundIdIn(roundIds);
        List<Long> performerIds = roundPerformers.stream()
                .map(RoundPerformer::getPerformerId)
                .collect(Collectors.toList());

        Map<Long, Performer> performerMapById = performerService.findPerformersMapById(showId, performerIds);

        Map<Long, List<Performer>> performersMapByRoundId = roundPerformerRepository.findByRoundIdIn(roundIds).stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId, Collectors.mapping(roundPerformer -> performerMapById.get(roundPerformer.getPerformerId()), Collectors.toList())));

        return rounds.stream()
                .map(round -> RoundResponse.from(round, performersMapByRoundId.getOrDefault(round.getRoundId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }
}
