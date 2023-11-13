package com.culture.ticketing.place.application;

import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.infra.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional
    public void createPlace(PlaceSaveRequest request) {

        Place place = request.toEntity();
        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public Place getPlaceByPlaceId(Long placeId) {

        return placeRepository.findById(placeId).orElseThrow();
    }
}
