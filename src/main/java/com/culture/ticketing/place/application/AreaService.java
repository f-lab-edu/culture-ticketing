package com.culture.ticketing.place.application;

import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest;
import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.exception.PlaceNotFoundException;
import com.culture.ticketing.place.infra.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final PlaceService placeService;

    public AreaService(AreaRepository areaRepository, PlaceService placeService) {
        this.areaRepository = areaRepository;
        this.placeService = placeService;
    }

    @Transactional(readOnly = true)
    public boolean notExistsById(Long areaId) {
        return !areaRepository.existsById(areaId);
    }

    @Transactional
    public void createPlaceArea(PlaceAreaSaveRequest request) {

        Objects.requireNonNull(request.getPlaceId(), "장소 아이디를 입력해주세요.");

        if (placeService.notExistsById(request.getPlaceId())) {
            throw new PlaceNotFoundException(request.getPlaceId());
        }

        Area area = request.toEntity();
        areaRepository.save(area);
    }
}
