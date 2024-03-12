package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowAreaGradeService;
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeSaveRequest;
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

@Api(tags = {"공연 구역 등급 Controller"})
@RestController
@RequestMapping("/api/v1/show-area-grades")
public class ShowAreaGradeController {

    private final ShowAreaGradeService showAreaGradeService;

    public ShowAreaGradeController(ShowAreaGradeService showAreaGradeService) {
        this.showAreaGradeService = showAreaGradeService;
    }

    @ApiOperation(value = "공연 구역 등급 생성 API")
    @PostMapping
    public void postShowAreaGrade(@RequestBody ShowAreaGradeSaveRequest request) {

        showAreaGradeService.createShowAreaGrade(request);
    }

    @ApiOperation(value = "공연 구역 등급 목록 조회 API")
    @GetMapping
    public List<ShowAreaGradeResponse> getShowAreaGradesByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return showAreaGradeService.findShowAreaGradesByShowId(showId);
    }
}
