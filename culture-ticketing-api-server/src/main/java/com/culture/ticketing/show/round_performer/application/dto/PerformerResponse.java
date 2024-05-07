package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Performer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "출연자 응답 DTO")
@Getter
public class PerformerResponse {

    @Schema(description = "출연자 아이디")
    private final Long performerId;
    @Schema(description = "출연자명")
    private final String performerName;
    @Schema(description = "출연자 이미지 url")
    private final String performerImgUrl;
    @Schema(description = "역할/담당")
    private final String role;

    public PerformerResponse(Performer performer) {
        this.performerId = performer.getPerformerId();
        this.performerName = performer.getPerformerName();
        this.performerImgUrl = performer.getPerformerImgUrl();
        this.role = performer.getRole();
    }
}
