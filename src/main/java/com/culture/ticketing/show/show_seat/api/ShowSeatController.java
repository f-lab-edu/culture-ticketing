package com.culture.ticketing.show.show_seat.api;

import com.culture.ticketing.show.show_seat.application.ShowSeatFacadeService;
import com.culture.ticketing.show.show_seat.application.ShowSeatService;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatSaveRequest;
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
    private final ShowSeatFacadeService showSeatFacadeService;

    public ShowSeatController(ShowSeatService showSeatService, ShowSeatFacadeService showSeatFacadeService) {
        this.showSeatService = showSeatService;
        this.showSeatFacadeService = showSeatFacadeService;
    }

    @ApiOperation(value = "공연 좌석 생성 API")
    @PostMapping
    public void postShowSeat(@RequestBody ShowSeatSaveRequest request) {

        showSeatService.createShowSeat(request);
    }

    @ApiOperation(value = "회차 아이디와 구역 아이디로 공연 좌석 목록 조회 API")
    @GetMapping
    public List<ShowSeatResponse> getShowSeats(@ApiParam(value = "회차 아이디") @RequestParam("roundId") Long roundId,
                                               @ApiParam(value = "구역 아이디") @RequestParam("areaId") Long areaId) {

        return showSeatFacadeService.findByRoundIdAndAreaId(roundId, areaId);
    }
}
