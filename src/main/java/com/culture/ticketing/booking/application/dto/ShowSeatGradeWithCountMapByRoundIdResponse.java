package com.culture.ticketing.booking.application.dto;

import com.culture.ticketing.show.show_seat.application.dto.ShowSeatCountMapByShowSeatGradeIdResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeWithCountResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowSeatGradeWithCountMapByRoundIdResponse {

    private final Map<Long, List<ShowSeatGradeWithCountResponse>> showSeatGradeWithCountMapByRoundId;

    public ShowSeatGradeWithCountMapByRoundIdResponse(List<Long> roundIds,
                                                      List<ShowSeatGradeResponse> showSeatGrades,
                                                      ShowSeatCountMapByShowSeatGradeIdResponse showSeatCountMapByShowSeatGradeId,
                                                      BookingShowSeatsMapByRoundIdResponse bookingShowSeatsMapByRoundId) {
        this.showSeatGradeWithCountMapByRoundId = roundIds.stream()
                .collect(Collectors.toMap(Function.identity(), roundId -> showSeatGrades.stream()
                        .map(showSeatGrade -> new ShowSeatGradeWithCountResponse(showSeatGrade, showSeatCountMapByShowSeatGradeId.getShowSeatCountByShowSeatGradeId(showSeatGrade.getShowSeatGradeId())
                                - bookingShowSeatsMapByRoundId.getBookingShowSeatCountByRoundIdAndShowSeatGradeId(roundId, showSeatGrade.getShowSeatGradeId()))
                        )
                        .collect(Collectors.toList())
                ));
    }

    public List<ShowSeatGradeWithCountResponse> getByRoundId(Long roundId) {
        return showSeatGradeWithCountMapByRoundId.get(roundId);
    }
}
