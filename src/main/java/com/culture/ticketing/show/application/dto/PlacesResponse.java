package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.exception.PlaceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class PlacesResponse {

    private final List<PlaceResponse> places;

    public PlacesResponse(List<Place> places) {
        this.places = places.stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    public PlaceResponse getByPlaceId(Long placeId) {

        return this.places.stream()
                .filter(place -> place.getPlaceId().equals(placeId))
                .findAny()
                .orElseThrow(() -> {
                    throw new PlaceNotFoundException(placeId);
                });
    }
}
