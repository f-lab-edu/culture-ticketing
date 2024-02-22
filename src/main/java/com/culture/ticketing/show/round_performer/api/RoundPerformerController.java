package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.round_performer.application.RoundPerformerService;
import com.culture.ticketing.show.round_performer.application.dto.RoundPerformersSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"회차 출연자 Controller"})
@RestController
@RequestMapping("/api/v1/round-performers")
public class RoundPerformerController {

    private final RoundPerformerService roundPerformerService;

    public RoundPerformerController(RoundPerformerService roundPerformerService) {
        this.roundPerformerService = roundPerformerService;
    }

    @ApiOperation(value = "회차 출연자 목록 생성 API")
    @PostMapping
    public void postRoundPerformers(@RequestBody RoundPerformersSaveRequest request) {

        roundPerformerService.createRoundPerformers(request);
    }
}
