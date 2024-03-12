package com.culture.ticketing.show.round_performer.api;

import com.culture.ticketing.show.round_performer.application.PerformerService;
import com.culture.ticketing.show.round_performer.application.dto.PerformerResponse;
import com.culture.ticketing.show.round_performer.application.dto.PerformerSaveRequest;
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

@Api(tags = {"출연자 Controller"})
@RestController
@RequestMapping("/api/v1/performers")
public class PerformerController {

    private final PerformerService performerService;

    public PerformerController(PerformerService performerService) {
        this.performerService = performerService;
    }

    @ApiOperation(value = "출연자 생성 API")
    @PostMapping
    public void postPerformer(@RequestBody PerformerSaveRequest request) {

        performerService.createPerformer(request);
    }

    @ApiOperation(value = "공연 아이디로 출연자 목록 조회 API")
    @GetMapping
    public List<PerformerResponse> getPerformersByShowId(@ApiParam(value = "공연 아이디") @RequestParam(value = "showId") Long showId) {

        return performerService.findPerformersByShowId(showId);
    }

}
