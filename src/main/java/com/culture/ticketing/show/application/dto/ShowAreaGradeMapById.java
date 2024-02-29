package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowAreaGrade;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowAreaGradeMapById {

    private final Map<Long, ShowAreaGrade> showAreaGradeMapById;

    public ShowAreaGradeMapById(Collection<ShowAreaGrade> showAreaGrades) {

        this.showAreaGradeMapById = showAreaGrades.stream()
                .collect(Collectors.toMap(ShowAreaGrade::getShowAreaGradeId, Function.identity()));
    }

    public ShowAreaGrade getById(Long showAreaGradeId) {
        return showAreaGradeMapById.get(showAreaGradeId);
    }
}
