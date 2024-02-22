package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "공연 상세 응답 DTO")
@Getter
public class ShowDetailResponse {

    @Schema(description = "공연 정보")
    private final ShowResponse show;
    @Schema(description = "회차 및 출연자 목록 정보")
    private final List<RoundWithPerformersResponse> rounds;
    @Schema(description = "공연 좌석 등급 목록 정보")
    private final List<ShowSeatGradeResponse> showSeatGrades;
    @Schema(description = "공연 플로어 등급 목록 정보")
    private final List<ShowFloorGradeResponse> showFloorGrades;

    @Builder
    public ShowDetailResponse(ShowResponse show, List<RoundWithPerformersResponse> rounds,
                              List<ShowSeatGradeResponse> showSeatGrades, List<ShowFloorGradeResponse> showFloorGrades) {
        this.show = show;
        this.rounds = rounds;
        this.showSeatGrades = showSeatGrades;
        this.showFloorGrades = showFloorGrades;
    }

    public static ShowDetailResponse from(Show show, Place place, List<RoundWithPerformersResponse> rounds,
                                          List<ShowSeatGradeResponse> showSeatGrades, List<ShowFloorGradeResponse> showFloorGrades) {
        return ShowDetailResponse.builder()
                .show(ShowResponse.from(show, place))
                .rounds(rounds)
                .showSeatGrades(showSeatGrades)
                .showFloorGrades(showFloorGrades)
                .build();
    }
}
