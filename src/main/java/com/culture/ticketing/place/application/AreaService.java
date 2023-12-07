package com.culture.ticketing.place.application;

import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest;
import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.place.infra.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PLACE_ID;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final PlaceService placeService;

    public AreaService(AreaRepository areaRepository, PlaceService placeService) {
        this.areaRepository = areaRepository;
        this.placeService = placeService;
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long areaId) {
        return areaRepository.existsById(areaId);
    }

    @Transactional
    public void createPlaceArea(PlaceAreaSaveRequest request) {

        Objects.requireNonNull(request.getPlaceId(), EMPTY_PLACE_ID.getMessage());

        if (!placeService.existsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        Area area = request.toEntity();
        areaRepository.save(area);
    }
}
