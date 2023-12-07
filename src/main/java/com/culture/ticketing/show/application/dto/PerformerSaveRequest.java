package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.Performer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PerformerSaveRequest {

    @NotNull
    private Long showId;
    @NotBlank
    private String performerName;
    private String performerImgUrl;
    private String role;

    public Performer toEntity() {
        return Performer.builder()
                .showId(showId)
                .performerName(performerName)
                .performerImgUrl(performerImgUrl)
                .role(role)
                .build();
    }
}
