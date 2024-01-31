package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    private Long showSeatGradeId;
    private Set<Long> seatIds;

    @Builder
    public ShowSeatSaveRequest(Long showSeatGradeId, Set<Long> seatIds) {
        this.showSeatGradeId = showSeatGradeId;
        this.seatIds = seatIds;
    }

    public List<ShowSeat> toEntities() {
        return seatIds.stream()
                .map(seatId -> ShowSeat.builder()
                        .showSeatGradeId(showSeatGradeId)
                        .seatId(seatId)
                        .build())
                .collect(Collectors.toList());
    }
}
