package com.culture.ticketing.show.api;

import com.culture.ticketing.show.application.ShowAreaGradeService;
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse;
import com.culture.ticketing.show.application.dto.ShowAreaGradeSaveRequest;
import com.culture.ticketing.show.application.dto.ShowAreaGradesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/show-area-grades")
public class ShowAreaGradeController {

    private final ShowAreaGradeService showAreaGradeService;

    public  ShowAreaGradeController(ShowAreaGradeService showAreaGradeService) {
        this.showAreaGradeService = showAreaGradeService;
    }

    @PostMapping
    public void postShowAreaGrade(@RequestBody ShowAreaGradeSaveRequest request) {

        showAreaGradeService.createShowAreaGrade(request);
    }

    @GetMapping
    public ShowAreaGradesResponse getShowAreaGradesByShowId(@RequestParam("showId") Long showId) {

        return showAreaGradeService.findShowAreaGradesByShowId(showId);
    }
}
