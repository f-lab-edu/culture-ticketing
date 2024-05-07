package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.ShowSeatService;
import com.culture.ticketing.show.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest;
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

@Api(tags = {"공연 좌석 Controller"})
@RestController
@RequestMapping("/api/v1/show-seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;
    private final ShowFacadeService showFacadeService;

    public ShowSeatController(ShowSeatService showSeatService, ShowFacadeService showFacadeService) {
        this.showSeatService = showSeatService;
        this.showFacadeService = showFacadeService;
    }

    @ApiOperation(value = "공연 좌석 생성 API")
    @PostMapping
    public void postShowSeat(@RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }

    @ApiOperation(value = "공연 좌석 목록 조회 API")
    @GetMapping
    public List<ShowSeatResponse> getShowSeatsByShowAreaIdAndRoundId(@ApiParam(value = "공연 구역 아이디") @RequestParam("showAreaId") Long showAreaId,
                                                                     @ApiParam(value = "회차 아이디") @RequestParam("roundId") Long roundId) {

        return showFacadeService.findShowSeatsByShowAreaIdAndRoundId(showAreaId, roundId);
    }
}
