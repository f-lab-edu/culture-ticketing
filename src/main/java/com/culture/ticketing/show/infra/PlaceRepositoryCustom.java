package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Place;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> findByPlaceIdGreaterThanLimit(Long placeId, int size);
}
