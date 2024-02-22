package com.culture.ticketing.show.show_floor.api;

import com.culture.ticketing.show.show_floor.application.dto.ShowFloorResponse;
import com.culture.ticketing.show.show_floor.application.ShowFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"공연 플로어 Controller"})
@RestController
@RequestMapping("/api/v1/show-floors")
public class ShowFloorController {

    private final ShowFloorService showFloorService;

    public ShowFloorController(ShowFloorService showFloorService) {
        this.showFloorService = showFloorService;
    }

    @ApiOperation(value = "공연 플로어 생성 API")
    @PostMapping
    public void postShowFloor(@RequestBody ShowFloorSaveRequest request) {

        showFloorService.createShowFloor(request);
    }

    @ApiOperation(value = "공연 플로어 목록 조회 API")
    @GetMapping
    public List<ShowFloorResponse> getShowFloorsByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return showFloorService.findByShowId(showId);
    }
}
