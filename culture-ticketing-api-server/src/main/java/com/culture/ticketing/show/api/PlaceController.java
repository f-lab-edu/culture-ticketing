package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.PlaceService;
import com.culture.ticketing.show.application.dto.PlaceResponse;
import com.culture.ticketing.show.application.dto.PlaceSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
