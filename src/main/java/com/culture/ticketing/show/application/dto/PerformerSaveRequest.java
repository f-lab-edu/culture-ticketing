package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Performer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformerSaveRequest {

    private Long showId;
    private String performerName;
    private String performerImgUrl;
    private String role;

    @Builder
    public PerformerSaveRequest(Long showId, String performerName, String performerImgUrl, String role) {
        this.showId = showId;
        this.performerName = performerName;
        this.performerImgUrl = performerImgUrl;
        this.role = role;
    }

    public Performer toEntity() {
        return Performer.builder()
                .showId(showId)
                .performerName(performerName)
                .performerImgUrl(performerImgUrl)
                .role(role)
                .build();
    }
}
