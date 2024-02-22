package com.culture.ticketing.place.api;

import com.culture.ticketing.place.application.PlaceService;
import com.culture.ticketing.place.application.dto.PlaceResponse;
import com.culture.ticketing.place.application.dto.PlaceSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"장소 Controller"})
@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ApiOperation(value = "장소 생성 API")
    @PostMapping
    public void postPlace(@RequestBody PlaceSaveRequest request) {

        placeService.createPlace(request);
    }

    @ApiOperation(value = "장소 목록 조회 API")
    @GetMapping
    public List<PlaceResponse> getPlaces(@ApiParam(value = "처음 시작 위치") @RequestParam(name = "offset") Long offset,
                                         @ApiParam(value = "사이즈") @RequestParam(name = "size") int size) {

        return placeService.findPlaces(offset, size);
    }

}
