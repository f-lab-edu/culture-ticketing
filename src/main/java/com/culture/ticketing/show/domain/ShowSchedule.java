package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_schedule")
public class ShowSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_schedule_id", nullable = false, updatable = false)
    private Long showScheduleId;
    @Column(name = "show_schedule_date", nullable = false)
    private LocalDate showScheduleDate;
    @Column(name = "show_schedule_time", nullable = false)
    private LocalTime showScheduleTime;
    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Builder
    public ShowSchedule(LocalDate showScheduleDate, LocalTime showScheduleTime, Long showId) {

        if (showId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_ID);
        }
        if (showScheduleDate == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SCHEDULE_DATE);
        }
        if (showScheduleTime == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_SCHEDULE_TIME);
        }

        this.showScheduleDate = showScheduleDate;
        this.showScheduleTime = showScheduleTime;
        this.showId = showId;
    }
}
