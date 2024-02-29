package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.exception.ShowAreaNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowAreaResponseMapById {

    private final Map<Long, ShowAreaResponse> showAreaResponseMapById;

    public ShowAreaResponseMapById(Collection<ShowAreaResponse> showAreaResponses) {

        this.showAreaResponseMapById = showAreaResponses.stream()
                .collect(Collectors.toMap(ShowAreaResponse::getShowAreaId, Function.identity()));
    }

    public ShowAreaResponse getById(Long showAreaId) {

        if (!showAreaResponseMapById.containsKey(showAreaId)) {
            throw new ShowAreaNotFoundException(showAreaId);
        }

        return showAreaResponseMapById.get(showAreaId);
    }
}
