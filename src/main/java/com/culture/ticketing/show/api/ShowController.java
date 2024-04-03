package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowFacadeService;
import com.culture.ticketing.show.application.ShowLikeService;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowDetailResponse;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.user.domain.SecurityUser;
import com.culture.ticketing.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"공연 Controller"})
@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowFacadeService showFacadeService;
    private final ShowLikeService showLikeService;

    public ShowController(ShowService showService, ShowFacadeService showFacadeService, ShowLikeService showLikeService) {
        this.showService = showService;
        this.showFacadeService = showFacadeService;
        this.showLikeService = showLikeService;
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
    public ShowDetailResponse getShowById(@PathVariable("showId") Long showId, final Authentication authentication) {

        User user = null;
        if (authentication != null) {
            user = ((SecurityUser) authentication.getPrincipal()).getUser();
        }

        return showFacadeService.findShowById(user, showId);
    }

    @ApiOperation(value = "공연 좋아요 생성 API")
    @PostMapping("/{showId}/likes")
    public void createShowLike(@PathVariable("showId") Long showId, final Authentication authentication) {

        User user = ((SecurityUser) authentication.getPrincipal()).getUser();

        showLikeService.createShowLike(user, showId);
    }

    @ApiOperation(value = "공연 좋아요 삭제 API")
    @DeleteMapping("/{showId}/likes")
    public void deleteShowLike(@PathVariable("showId") Long showId, final Authentication authentication) {

        User user = ((SecurityUser) authentication.getPrincipal()).getUser();

        showLikeService.deleteShowLike(user, showId);
    }
}
