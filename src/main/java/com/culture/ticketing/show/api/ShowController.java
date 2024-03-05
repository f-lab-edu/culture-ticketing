package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.domain.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"공연 Controller"})
@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowFacadeService showFacadeService;

    public ShowController(ShowService showService, ShowFacadeService showFacadeService) {
        this.showService = showService;
        this.showFacadeService = showFacadeService;
    }

    @ApiOperation(value = "공연 생성 API")
    @PostMapping
    public void postShow(@RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @ApiOperation(value = "공연 목록 조회 API")
    @GetMapping
    public List<ShowResponse> getShows(@ApiParam(value = "처음 시작 위치") @RequestParam(name = "offset") Long offset,
                                       @ApiParam(value = "사이즈") @RequestParam(name = "size") int size,
                                       @ApiParam(value = "카테고리") @RequestParam(name = "category", required = false) Category category) {

        return showService.findShows(offset, size, category);
    }

    @ApiOperation(value = "공연 상세 조회 API")
    @GetMapping("/{showId}")
    public ShowDetailResponse getShowById(@PathVariable("showId") Long showId) {

        return showFacadeService.findShowById(showId);
    }
}
