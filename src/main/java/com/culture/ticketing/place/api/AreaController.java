package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.AreaService;
import com.culture.ticketing.place.application.dto.PlaceAreaSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"장소 구역 Controller"})
@RestController
@RequestMapping("/api/v1/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @ApiOperation(value = "장소 구역 생성 API")
    @PostMapping
    public void postArea(@RequestBody PlaceAreaSaveRequest request) {

        areaService.createPlaceArea(request);
    }
}
