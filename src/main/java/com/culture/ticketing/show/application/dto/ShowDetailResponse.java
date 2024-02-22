package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShowDetailResponse {

    private final ShowResponse show;
    private final List<RoundWithPerformersResponse> rounds;
    private final List<ShowSeatGradeResponse> showSeatGrades;
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
