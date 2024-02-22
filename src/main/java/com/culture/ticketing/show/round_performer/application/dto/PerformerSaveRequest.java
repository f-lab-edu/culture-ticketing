package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.round_performer.domain.Performer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "출연자 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class PerformerSaveRequest {

    @Schema(description = "공연 아이디")
    private Long showId;
    @Schema(description = "출연자명")
    private String performerName;
    @Schema(description = "출연자 이미지 url")
    private String performerImgUrl;
    @Schema(description = "역할/담당")
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
