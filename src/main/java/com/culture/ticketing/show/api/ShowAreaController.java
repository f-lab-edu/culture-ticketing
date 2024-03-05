package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowAreaService;
import com.culture.ticketing.show.application.dto.ShowAreaSaveRequest;
import com.culture.ticketing.show.application.dto.ShowAreasResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"공연 구역 Controller"})
@RestController
@RequestMapping("/api/v1/show-areas")
public class ShowAreaController {

    private final ShowAreaService showAreaService;

    public ShowAreaController(ShowAreaService showAreaService) {
        this.showAreaService = showAreaService;
    }

    @ApiOperation(value = "공연 구역 생성 API")
    @PostMapping
    public void postShowArea(@RequestBody ShowAreaSaveRequest request) {

        showAreaService.createShowArea(request);
    }

    @ApiOperation(value = "공연 구역 목록 조회 API")
    @GetMapping
    public ShowAreasResponse getShowAreasByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return showAreaService.findShowAreasByShowId(showId);
    }
}
