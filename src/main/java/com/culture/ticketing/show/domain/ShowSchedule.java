package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SCHEDULE_DATE;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_SCHEDULE_TIME;

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

        Objects.requireNonNull(showId, EMPTY_SHOW_ID.getMessage());
        Objects.requireNonNull(showScheduleDate, EMPTY_SHOW_SCHEDULE_DATE.getMessage());
        Objects.requireNonNull(showScheduleTime, EMPTY_SHOW_SCHEDULE_TIME.getMessage());

        this.showScheduleDate = showScheduleDate;
        this.showScheduleTime = showScheduleTime;
        this.showId = showId;
    }
}
