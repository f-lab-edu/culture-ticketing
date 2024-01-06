package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.RoundPerformer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoundPerformersSaveRequest {

    @NotNull
    private Long roundId;
    @NotNull
    private Set<Long> performerIds;

    @Builder
    public RoundPerformersSaveRequest(Long roundId, Set<Long> performerIds) {
        this.roundId = roundId;
        this.performerIds = performerIds;
    }

    public List<RoundPerformer> toEntities() {
        return performerIds.stream()
                .map(performerId -> RoundPerformer.builder()
                        .roundId(roundId)
                        .performerId(performerId)
                        .build())
                .collect(Collectors.toList());
    }
}
