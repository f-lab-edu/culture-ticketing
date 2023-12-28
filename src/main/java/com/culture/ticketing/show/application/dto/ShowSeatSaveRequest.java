package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowSeat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    @NotNull
    private Long showSeatGradeId;
    @NotNull
    private List<Long> seatIds;

    public List<ShowSeat> toEntities() {
        return seatIds.stream()
                .map(seatId -> ShowSeat.builder()
                        .showSeatGradeId(showSeatGradeId)
                        .seatId(seatId)
                        .build())
                .collect(Collectors.toList());
    }
}
