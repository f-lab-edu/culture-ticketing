package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.infra.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional
    public void createPlace(PlaceSaveRequest request) {

        if (!StringUtils.hasText(request.getAddress())) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ADDRESS);
        }
        if (request.getLatitude() == null || request.getLatitude().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LATITUDE);
        }
        if (request.getLongitude() == null || request.getLongitude().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LONGITUDE);
        }

        Place place = request.toEntity();
        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long placeId) {
        return placeRepository.existsById(placeId);
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> getPlaces(Long offset, int size) {

        return placeRepository.findByPlaceIdGreaterThanLimit(offset, size).stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Place> findByIdIn(List<Long> placeIds) {
        return placeRepository.findAllById(placeIds);
    }
}
