package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Performer;
import com.culture.ticketing.show.round_performer.exception.PerformerNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PerformersResponse {

    private final List<PerformerResponse> performers;

    public PerformersResponse(List<Performer> performers) {
        this.performers = performers.stream()
                .map(PerformerResponse::new)
                .collect(Collectors.toList());
    }

    public PerformerResponse getByPerformerId(Long performerId) {

        return performers.stream()
                .filter(performer -> performer.getPerformerId().equals(performerId))
                .findAny()
                .orElseThrow(() -> {
                    throw new PerformerNotFoundException(performerId);
                });
    }

    public List<PerformerResponse> getPerformers() {

        return Collections.unmodifiableList(performers);
    }
}
