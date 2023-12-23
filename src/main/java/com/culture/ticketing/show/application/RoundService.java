package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.RoundResponse;
import com.culture.ticketing.show.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.domain.Round;
import com.culture.ticketing.show.domain.RoundPerformer;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException;
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTime;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.exception.ShowPerformerNotMatchException;
import com.culture.ticketing.show.infra.PerformerRepository;
import com.culture.ticketing.show.infra.RoundPerformerRepository;
import com.culture.ticketing.show.infra.RoundRepository;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_ROUND_DATE_TIME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;


@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundPerformerRepository roundPerformerRepository;
    private final PerformerRepository performerRepository;
    private final ShowRepository showRepository;

    public RoundService(RoundRepository roundRepository, RoundPerformerRepository roundPerformerRepository,
                        PerformerRepository performerRepository, ShowRepository showRepository) {
        this.roundRepository = roundRepository;
        this.roundPerformerRepository = roundPerformerRepository;
        this.performerRepository = performerRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public void createRound(RoundSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(request.getRoundStartDateTime(), EMPTY_ROUND_DATE_TIME.getMessage());

        Show show = showRepository.findById(request.getShowId()).orElseThrow(() -> {
            throw new ShowNotFoundException(request.getShowId());
        });
        Round round = request.toEntity(show);

        checkOutOfRangeRoundDateTime(round, show);
        checkDuplicatedRoundDateTime(round);

        Round saveRound = roundRepository.save(round);

        checkShowPerformerMatch(round.getShowId(), request.getPerformerIds());
        List<RoundPerformer> roundPerformers = request.getPerformerIds().stream()
                .map(performerId -> RoundPerformer.builder()
                        .roundId(saveRound.getRoundId())
                        .performerId(performerId)
                        .build())
                .collect(Collectors.toList());
        roundPerformerRepository.saveAll(roundPerformers);
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

    private void checkShowPerformerMatch(Long showId, Set<Long> performerIds) {

        List<Performer> foundPerformers = performerRepository.findByShowIdAndPerformerIdIn(showId, performerIds);
        if (foundPerformers.size() != performerIds.size()) {
            String notMatchingPerformerIds = performerIds.stream()
                    .filter(performerId -> foundPerformers.stream().noneMatch(performer -> performer.getPerformerId().equals(performerId)))
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));
            throw new ShowPerformerNotMatchException(notMatchingPerformerIds);
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

        Map<Long, Performer> performerMapById = performerRepository.findByShowIdAndPerformerIdIn(showId, performerIds).stream()
                .collect(Collectors.toMap(Performer::getPerformerId, Function.identity()));

        Map<Long, List<Performer>> performersMapByRoundId = roundPerformerRepository.findByRoundIdIn(roundIds).stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId, Collectors.mapping(roundPerformer -> performerMapById.get(roundPerformer.getPerformerId()), Collectors.toList())));

        return rounds.stream()
                .map(round -> RoundResponse.from(round, performersMapByRoundId.getOrDefault(round.getRoundId(), Collections.emptyList())))
                .collect(Collectors.toList());
    }
}
