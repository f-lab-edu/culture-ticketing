package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.exception.ShowAreaGradeNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowAreaGradeResponseMapById {

    private final Map<Long, ShowAreaGradeResponse> showAreaGradeResponseMapById;

    public ShowAreaGradeResponseMapById(Collection<ShowAreaGradeResponse> showAreaGrades) {

        this.showAreaGradeResponseMapById = showAreaGrades.stream()
                .collect(Collectors.toMap(ShowAreaGradeResponse::getShowAreaGradeId, Function.identity()));
    }

    public ShowAreaGradeResponse getById(Long showAreaGradeId) {

        if (!showAreaGradeResponseMapById.containsKey(showAreaGradeId)) {
            throw new ShowAreaGradeNotFoundException(showAreaGradeId);
        }

        return showAreaGradeResponseMapById.get(showAreaGradeId);
    }
}
