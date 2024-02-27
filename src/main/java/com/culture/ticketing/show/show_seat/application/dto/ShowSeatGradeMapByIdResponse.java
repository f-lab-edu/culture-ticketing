package com.culture.ticketing.show.show_seat.application.dto;

import com.culture.ticketing.show.show_seat.domain.ShowSeatGrade;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowSeatGradeMapByIdResponse {

    private final Map<Long, ShowSeatGrade> showSeatGradeMapById;

    public ShowSeatGradeMapByIdResponse(List<ShowSeatGrade> showSeatGrades) {

        this.showSeatGradeMapById = showSeatGrades.stream()
                .collect(Collectors.toMap(ShowSeatGrade::getShowSeatGradeId, Function.identity()));
    }

    public ShowSeatGrade getById(Long showSeatGradeId) {
        return showSeatGradeMapById.get(showSeatGradeId);
    }
}
