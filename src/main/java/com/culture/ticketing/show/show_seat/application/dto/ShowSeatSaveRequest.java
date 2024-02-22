package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(description = "공연 좌석 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class ShowSeatSaveRequest {

    @Schema(description = "공연 좌석 등급 아이디")
    private Long showSeatGradeId;
    @Schema(description = "좌석 아이디 목록")
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
