package com.culture.ticketing.place.infra;

import com.culture.ticketing.place.domain.Place;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> findByPlaceIdGreaterThanLimit(Long placeId, int size);
}
