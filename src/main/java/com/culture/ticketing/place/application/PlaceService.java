package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.place.infra.PlaceRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.culture.ticketing.common.response.BaseResponseStatus.*;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional
    public void createPlace(PlaceSaveRequest request) {

        Objects.requireNonNull(request.getLatitude(), EMPTY_PLACE_LATITUDE.getMessage());
        Objects.requireNonNull(request.getLongitude(), EMPTY_PLACE_LONGITUDE.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getAddress()), EMPTY_PLACE_ADDRESS.getMessage());
        Preconditions.checkArgument(request.getLatitude().compareTo(BigDecimal.valueOf(-90)) >= 0
                && request.getLatitude().compareTo(BigDecimal.valueOf(90)) <= 0, PLACE_LATITUDE_OUT_OF_RANGE.getMessage());
        Preconditions.checkArgument(request.getLongitude().compareTo(BigDecimal.valueOf(-180)) >= 0
                && request.getLongitude().compareTo(BigDecimal.valueOf(180)) <= 0, PLACE_LONGITUDE_OUT_OF_RANGE.getMessage());

        Place place = request.toEntity();
        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public Place getPlaceByPlaceId(Long placeId) {

        return placeRepository.findById(placeId).orElseThrow(() -> {
            throw new PlaceNotFoundException(placeId);
        });
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> getPlaces(Long lastPlaceId, int size) {

        return placeRepository.findByPlaceIdGreaterThanLimit(lastPlaceId, size).stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }
}
