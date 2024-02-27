package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.show.show_floor.application.dto.ShowFloorCountMapByShowFloorGradeIdResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeWithCountResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowFloorGradeWithCountMapByRoundIdResponse {

    private final Map<Long, List<ShowFloorGradeWithCountResponse>> showFloorGradeWithCountMapByRoundId;


    public ShowFloorGradeWithCountMapByRoundIdResponse(List<Long> roundIds,
                                                       List<ShowFloorGradeResponse> showFloorGrades,
                                                       ShowFloorCountMapByShowFloorGradeIdResponse showFloorCountMapByShowSeatGradeId,
                                                       BookingShowFloorsMapByRoundIdResponse bookingShowFloorsMapByRoundId) {
        this.showFloorGradeWithCountMapByRoundId = roundIds.stream()
                .collect(Collectors.toMap(Function.identity(), roundId -> showFloorGrades.stream()
                        .map(showFloorGrade -> new ShowFloorGradeWithCountResponse(showFloorGrade, showFloorCountMapByShowSeatGradeId.getShowFloorCountByShowFloorGradeId(showFloorGrade.getShowFloorGradeId())
                                - bookingShowFloorsMapByRoundId.getBookingFloorCountByRoundIdAndShowFloorGradeId(roundId, showFloorGrade.getShowFloorGradeId()))
                        )
                        .collect(Collectors.toList())
                ));
    }

    public List<ShowFloorGradeWithCountResponse> getByRoundId(Long roundId) {

        return showFloorGradeWithCountMapByRoundId.get(roundId);
    }
}
