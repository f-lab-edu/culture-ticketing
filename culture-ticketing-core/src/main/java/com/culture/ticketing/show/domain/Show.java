package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Show extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_id", nullable = false, updatable = false)
    private Long showId;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @Column(name = "show_name", nullable = false)
    private String showName;
    @Enumerated(EnumType.STRING)
    @Column(name = "age_restriction", nullable = false)
    private AgeRestriction ageRestriction;
    @Column(name = "running_time", nullable = false)
    private int runningTime;
    @Column(name = "notice")
    private String notice;
    @Column(name = "poster_img_url", nullable = false)
    private String posterImgUrl;
    @Column(name = "description")
    private String description;
    @Column(name = "show_start_date", nullable = false)
    private LocalDate showStartDate;
    @Column(name = "show_end_date", nullable = false)
    private LocalDate showEndDate;
    @Column(name = "booking_start_date_time", nullable = false)
    private LocalDateTime bookingStartDateTime;
    @Column(name = "booking_end_date_time", nullable = false)
    private LocalDateTime bookingEndDateTime;
    @Column(name = "place_id")
    private Long placeId;

    @Builder
    public Show(Long showId, Category category, String showName, AgeRestriction ageRestriction,
                int runningTime, String notice, String posterImgUrl, String description,
                LocalDate showStartDate, LocalDate showEndDate,
                LocalDateTime bookingStartDateTime, LocalDateTime bookingEndDateTime, Long placeId) {

        Objects.requireNonNull(category, "공연 카테고리를 입력해주세요.");
        Objects.requireNonNull(ageRestriction, "공연 관람 제한가를 입력해주세요.");
        Objects.requireNonNull(placeId, "공연 장소 아이디를 입력해주세요.");
        Objects.requireNonNull(showStartDate, "공연 시작 날짜를 입력해주세요.");
        Objects.requireNonNull(showEndDate, "공연 종료 날짜를 입력해주세요.");
        Objects.requireNonNull(bookingEndDateTime, "예약 시작 일시를 입력해주세요.");
        Objects.requireNonNull(bookingEndDateTime, "예약 종료 일시를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(showName), "공연 이름을 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(posterImgUrl), "공연 포스터 이미지 url을 입력해주세요.");
        Preconditions.checkArgument(runningTime > 0, "공연 러닝 시간을 0 초과로 입력해주세요.");

        this.showId = showId;
        this.category = category;
        this.showName = showName;
        this.ageRestriction = ageRestriction;
        this.runningTime = runningTime;
        this.notice = notice;
        this.posterImgUrl = posterImgUrl;
        this.description = description;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.bookingStartDateTime = bookingStartDateTime;
        this.bookingEndDateTime = bookingEndDateTime;
        this.placeId = placeId;
    }
}
