package com.culture.ticketing.place.application;

import com.culture.ticketing.place.domain.Area;
import com.culture.ticketing.place.exception.AreaNotFoundException;
import com.culture.ticketing.place.infra.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Transactional(readOnly = true)
    public Area getAreaByAreaId(Long areaId) {
        return areaRepository.findById(areaId).orElseThrow(AreaNotFoundException::new);
    }
}
