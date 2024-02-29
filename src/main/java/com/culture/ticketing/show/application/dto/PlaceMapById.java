package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.exception.PlaceNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlaceMapById {

    private final Map<Long, Place> placeMapById;

    public PlaceMapById(Collection<Place> places) {
        this.placeMapById = places.stream()
                .collect(Collectors.toMap(Place::getPlaceId, Function.identity()));
    }

    public Place getById(Long placeId) {

        if (!placeMapById.containsKey(placeId)) {
            throw new PlaceNotFoundException(placeId);
        }

        return placeMapById.get(placeId);
    }
}
