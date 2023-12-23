package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.AreaService;
import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public void postArea(@Valid @RequestBody PlaceAreaSaveRequest request) {

        areaService.createPlaceArea(request);
    }
}
