package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Round;
import com.culture.ticketing.show.round_performer.domain.RoundPerformer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoundsWithPerformersResponse {

    private final List<RoundWithPerformersResponse> roundWithPerformers;

    public RoundsWithPerformersResponse(List<RoundPerformer> roundPerformers, List<Round> rounds, PerformersResponse performers) {

        Map<Long, List<PerformerResponse>> performersMapByRoundId = roundPerformers.stream()
                .collect(Collectors.groupingBy(RoundPerformer::getRoundId,
                        Collectors.mapping(roundPerformer -> performers.getByPerformerId(roundPerformer.getPerformerId()), Collectors.toList())));

        this.roundWithPerformers = rounds.stream()
                .map(round -> new RoundWithPerformersResponse(round, performersMapByRoundId.get(round.getRoundId())))
                .collect(Collectors.toList());
    }

    public List<RoundWithPerformersResponse> getRoundWithPerformers() {
        return Collections.unmodifiableList(this.roundWithPerformers);
    }

    public List<Long> getRoundIds() {

        return roundWithPerformers.stream()
                .map(RoundWithPerformersResponse::getRoundId)
                .collect(Collectors.toList());
    }
}
