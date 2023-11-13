package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.place.domain.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SHOWS")
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
    @Column(name = "show_start_date", nullable = false)
    private LocalDate showStartDate;
    @Column(name = "show_end_date", nullable = false)
    private LocalDate showEndDate;
    @Column(name = "running_time", nullable = false)
    private int runningTime;
    @Column(name = "notice")
    private String notice;
    @Column(name = "poster_img_url", nullable = false)
    private String posterImgUrl;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public Show(Category category, String showName, AgeRestriction ageRestriction,
                LocalDate showStartDate, LocalDate showEndDate, int runningTime,
                String notice, String posterImgUrl, String description, Place place) {
        this.category = category;
        this.showName = showName;
        this.ageRestriction = ageRestriction;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.runningTime = runningTime;
        this.notice = notice;
        this.posterImgUrl = posterImgUrl;
        this.description = description;
        this.place = place;
    }
}
