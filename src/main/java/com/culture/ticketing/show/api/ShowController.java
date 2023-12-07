package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.ShowSeatGradeService;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest;
import com.culture.ticketing.show.domain.Category;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;
    private final ShowSeatGradeService showSeatGradeService;

    public ShowController(ShowService showService, ShowSeatGradeService showSeatGradeService) {
        this.showService = showService;
        this.showSeatGradeService = showSeatGradeService;
    }

    @PostMapping("")
    public void postShow(@Valid @RequestBody ShowSaveRequest request) {

        showService.createShow(request);
    }

    @GetMapping("")
    public List<ShowResponse> getShows(@RequestParam(name = "offset") Long offset,
                                       @RequestParam(name = "size") int size,
                                       @RequestParam(name = "category", required = false) Category category) {

        return showService.findShows(offset, size, category);
    }

    @PostMapping("/seat-grades")
    public void postShowSeatGrade(@Valid @RequestBody ShowSeatGradeSaveRequest request) {

        showSeatGradeService.createShowSeatGrade(request);
    }
}
