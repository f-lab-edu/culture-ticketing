package com.culture.ticketing.show.round_performer.application.dto;

import com.culture.ticketing.show.show_floor.application.dto.ShowFloorGradeWithCountResponse;
import com.culture.ticketing.show.show_seat.application.dto.ShowSeatGradeWithCountResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "회차 및 출연자, 공연 좌석 목록 응답 DTO")
@Getter
public class RoundWithPerformersAndShowSeatsResponse {

    @Schema(description = "회차 아이디")
    private final Long roundId;
    @Schema(description = "회차 시작 일시")
    private final LocalDateTime roundStartDateTime;
    @Schema(description = "회차 종료 일시")
    private final LocalDateTime roundEndDateTime;
    @Schema(description = "출연자 목록 정보")
    private final List<PerformerResponse> performers;
    @Schema(description = "공연 좌석 등급별 예약 가능 좌석 수 목록 정보")
    private final List<ShowSeatGradeWithCountResponse> showSeatGrades;
    @Schema(description = "공연 플로어 등급별 예약 가능 좌석 수 목록 정보")
    private final List<ShowFloorGradeWithCountResponse> showFloorGrades;

    public RoundWithPerformersAndShowSeatsResponse(RoundWithPerformersResponse roundWithPerformers, List<ShowSeatGradeWithCountResponse> showSeatGrades, List<ShowFloorGradeWithCountResponse> showFloorGrades) {
        this.roundId = roundWithPerformers.getRoundId();
        this.roundStartDateTime = roundWithPerformers.getRoundStartDateTime();
        this.roundEndDateTime = roundWithPerformers.getRoundEndDateTime();
        this.performers = roundWithPerformers.getPerformers();
        this.showSeatGrades = showSeatGrades;
        this.showFloorGrades = showFloorGrades;
    }
}
