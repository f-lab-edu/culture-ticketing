package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PlaceResponse;
import com.culture.ticketing.show.application.dto.PlaceSaveRequest;
import com.culture.ticketing.show.domain.Place;
import com.culture.ticketing.show.exception.PlaceNotFoundException;
import com.culture.ticketing.show.infra.PlaceRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional
    public void createPlace(PlaceSaveRequest request) {

        checkValidPlaceSaveRequest(request);

        placeRepository.save(request.toEntity());
    }

    private void checkValidPlaceSaveRequest(PlaceSaveRequest request) {

        Objects.requireNonNull(request.getLatitude(), "정확한 장소 위도를 입력해주세요.");
        Objects.requireNonNull(request.getLongitude(), "정확한 장소 경도를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getAddress()), "장소 주소를 입력해주세요.");
        Preconditions.checkArgument(request.getLatitude().compareTo(BigDecimal.valueOf(-90)) >= 0
                && request.getLatitude().compareTo(BigDecimal.valueOf(90)) <= 0, "장소 위도 범위를 벗어난 입력값입니다.");
        Preconditions.checkArgument(request.getLongitude().compareTo(BigDecimal.valueOf(-180)) >= 0
                && request.getLongitude().compareTo(BigDecimal.valueOf(180)) <= 0, "장소 경도 범위를 벗어난 입력값입니다.");
    }

    @Transactional(readOnly = true)
    public PlaceResponse findPlaceById(Long placeId) {

        return placeRepository.findById(placeId)
                .map(PlaceResponse::new)
                .orElseThrow(() -> {
                    throw new PlaceNotFoundException(placeId);
                });
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long placeId) {
        return !placeRepository.existsById(placeId);
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findPlaces(Long offset, int size) {

        return getPlaceResponses(placeRepository.findByPlaceIdGreaterThanLimit(offset, size));
    }

    @Transactional(readOnly = true)
    public List<PlaceResponse> findPlacesByIds(List<Long> placeIds) {

        return getPlaceResponses(placeRepository.findAllById(placeIds));
    }

    private List<PlaceResponse> getPlaceResponses(List<Place> places) {

        return places.stream()
                .map(PlaceResponse::new)
                .collect(Collectors.toList());
    }
}
