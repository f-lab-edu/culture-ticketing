package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Performer;
import lombok.Getter;

@Getter
public class PerformerResponse {

    private final Long performerId;
    private final String performerName;
    private final String performerImgUrl;
    private final String role;

    public PerformerResponse(Performer performer) {
        this.performerId = performer.getPerformerId();
        this.performerName = performer.getPerformerName();
        this.performerImgUrl = performer.getPerformerImgUrl();
        this.role = performer.getRole();
    }
}
