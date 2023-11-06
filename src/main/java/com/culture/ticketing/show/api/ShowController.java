package com.culture.ticketing.show.api;

import com.culture.ticketing.common.response.BaseResponse;
import com.culture.ticketing.show.application.ShowService;
import com.culture.ticketing.show.application.dto.ShowSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.culture.ticketing.common.response.BaseResponse.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<Void> postShow(@RequestBody ShowSaveRequest request) {

        showService.createShow(request);

        return success();
    }
}
