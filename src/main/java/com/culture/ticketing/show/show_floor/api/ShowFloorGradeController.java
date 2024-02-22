package com.culture.ticketing.show.show_floor.api;

import com.culture.ticketing.show.show_floor.application.ShowFloorGradeService;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeResponse;
import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeSaveRequest;
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

@Api(tags = {"공연 플로어 등급 Controller"})
@RestController
@RequestMapping("/api/v1/show-floor-grades")
public class ShowFloorGradeController {

    private final ShowFloorGradeService showFloorGradeService;

    public ShowFloorGradeController(ShowFloorGradeService showFloorGradeService) {
        this.showFloorGradeService = showFloorGradeService;
    }

    @ApiOperation(value = "공연 플로어 등급 생성 API")
    @PostMapping
    public void postShowFloorGrade(@RequestBody ShowFloorGradeSaveRequest request) {

        showFloorGradeService.createShowFloorGrade(request);
    }

    @ApiOperation(value = "공연 아이디로 공연 플로어 등급 목록 조회 API")
    @GetMapping
    public List<ShowFloorGradeResponse> getShowFloorGradesByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return showFloorGradeService.findShowFloorGradesByShowId(showId);
    }
}
