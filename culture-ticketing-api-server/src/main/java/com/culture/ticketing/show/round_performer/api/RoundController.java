package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.round_performer.application.RoundService;
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse;
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest;
import com.culture.ticketing.show.round_performer.application.dto.RoundWithPerformersAndShowAreaGradesResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Api(tags = {"회차 Controller"})
@RestController
@RequestMapping("/api/v1/rounds")
public class RoundController {

    private final RoundService roundService;
    private final ShowFacadeService showFacadeService;

    public RoundController(RoundService roundService, ShowFacadeService showFacadeService) {
        this.roundService = roundService;
        this.showFacadeService = showFacadeService;
    }

    @ApiOperation(value = "회차 생성 API")
    @PostMapping
    public void postRound(@RequestBody RoundSaveRequest request) {

        roundService.createRound(request);
    }

    @ApiOperation(value = "공연 아이디로 회차 목록 조회 API")
    @GetMapping
    public List<RoundResponse> getRoundsByShowId(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId) {

        return roundService.findByShowId(showId);
    }

    @ApiOperation(value = "공연 아이디와 날짜로 회차 목록 조회 API")
    @GetMapping("/detail")
    public List<RoundWithPerformersAndShowAreaGradesResponse> getRoundsByShowIdAndRoundStartDate(@ApiParam(value = "공연 아이디") @RequestParam("showId") Long showId,
                                                                                                 @ApiParam(value = "회차 시작일") @RequestParam("roundStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate roundStartDate) {

        return showFacadeService.findRoundsByShowIdAndRoundStartDate(showId, roundStartDate);
    }
}
