package com.culture.ticketing.place.application;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest;
import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.exception.AreaNotFoundException;
import com.culture.ticketing.place.infra.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    private final PlaceService placeService;

    public AreaService(AreaRepository areaRepository, PlaceService placeService) {
        this.areaRepository = areaRepository;
        this.placeService = placeService;
    }

    @Transactional(readOnly = true)
    public Area getAreaByAreaId(Long areaId) {
        return areaRepository.findById(areaId).orElseThrow(AreaNotFoundException::new);
    }

    @Transactional
    public void createPlaceArea(PlaceAreaSaveRequest request) {

        if (request.getPlaceId() == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_PLACE_ID);
        }

        placeService.getPlaceByPlaceId(request.getPlaceId());
        Area area = request.toEntity();
        areaRepository.save(area);
    }
}
