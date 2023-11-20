package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import com.culture.ticketing.place.domain.Place;
import com.culture.ticketing.place.infra.PlaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

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
        if (request.getLatitude() == null || request.getLatitude().compareTo(BigDecimal.ZERO) > 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LATITUDE);
        }
        if (request.getLongitude() == null || request.getLongitude().compareTo(BigDecimal.ZERO) > 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_LONGITUDE);
        }

        Place place = request.toEntity();
        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public Place getPlaceByPlaceId(Long placeId) {

        return placeRepository.findById(placeId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<PlaceResponse> getPlaces(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return placeRepository.findAll(pageable).map(PlaceResponse::new);
    }
}
