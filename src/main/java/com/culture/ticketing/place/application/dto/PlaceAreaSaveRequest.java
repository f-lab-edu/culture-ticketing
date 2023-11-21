package com.culture.ticketing.place.application.dto;

import com.culture.ticketing.place.domain.Area;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PlaceAreaSaveRequest {

    private int coordinateX;
    private int coordinateY;
    @NotNull
    private Long placeId;

    public Area toEntity() {
        return Area.builder()
                .coordinateX(coordinateX)
                .coordinateY(coordinateY)
                .build();
    }
}
