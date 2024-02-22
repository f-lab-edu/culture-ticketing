package com.culture.ticketing.show.show_seat.api;

import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import com.culture.ticketing.show.show_seat.application.ShowSeatGradeService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"공연 좌석 등급 Controller"})
@RestController
@RequestMapping("/api/v1/show-seat-grades")
public class ShowSeatGradeController {

    private final ShowSeatGradeService showSeatGradeService;

    public ShowSeatGradeController(ShowSeatGradeService showSeatGradeService) {
        this.showSeatGradeService = showSeatGradeService;
    }

    @ApiOperation(value = "공연 좌석 등급 생성 API")
    @PostMapping
    public void postShowSeatGrade(@RequestBody ShowSeatGradeSaveRequest request) {

        showSeatGradeService.createShowSeatGrade(request);
    }

    @ApiOperation(value = "공연 아이디로 공연 좌석 등급 목록 조회 API")
    @GetMapping
    public List<ShowSeatGradeResponse> getShowSeatGradesByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return showSeatGradeService.findShowSeatGradesByShowId(showId);
    }
}
