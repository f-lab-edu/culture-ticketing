package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.RoundPerformer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "회차별 출연자 목록 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class RoundPerformersSaveRequest {

    @Schema(description = "회차 아이디")
    private Long roundId;
    @Schema(description = "출연자 아이디 목록")
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
