package com.culture.ticketing.show.application.dto;

import com.culture.ticketing.show.domain.ShowArea;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ShowAreaSaveRequest {

    @NotBlank
    private String showAreaName;
    private int coordinateX;
    private int coordinateY;
    @NotNull
    private Long showId;
    @NotNull
    private List<Long> areaIds;

    public List<ShowArea> toEntities() {
        return areaIds.stream()
                .map(areaId -> ShowArea.builder()
                        .showAreaName(showAreaName)
                        .coordinateX(coordinateX)
                        .coordinateY(coordinateY)
                        .showId(showId)
                        .areaId(areaId)
                        .build())
                .collect(Collectors.toList());
    }
}
