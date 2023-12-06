package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
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

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_ADDRESS;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_LATITUDE;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_LONGITUDE;
import static com.culture.ticketing.common.response.BaseResponseStatus.PLACE_LATITUDE_OUT_OF_RANGE;
import static com.culture.ticketing.common.response.BaseResponseStatus.PLACE_LONGITUDE_OUT_OF_RANGE;

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
    public boolean existsById(Long placeId) {
        return placeRepository.existsById(placeId);
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findPlaces(Long offset, int size) {

        return placeRepository.findByPlaceIdGreaterThanLimit(offset, size).stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Place> findByIdIn(List<Long> placeIds) {
        return placeRepository.findAllById(placeIds);
    }
}
