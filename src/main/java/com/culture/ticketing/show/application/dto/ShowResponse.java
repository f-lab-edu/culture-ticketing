package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Show;
import lombok.Getter;

@Getter
public class ShowResponse {

    private Long showId;
    private String posterImgUrl;
    private String scheduleStartDate;
    private String scheduleEndDate;
    private String placeName;

    public ShowResponse(Show show) {
        this.showId = show.getShowId();
        this.posterImgUrl = show.getPosterImgUrl();
    }
}
