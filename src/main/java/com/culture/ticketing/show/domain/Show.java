package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
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
    @Column(name = "showId", nullable = false, updatable = false)
    private Long showId;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String showName;
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;
    @Embedded
    private Place place;
    private LocalDate showStartDate;
    private LocalDate showEndDate;
    private int runningTime;
    private String notice;
    private String posterImgUrl;
    private String description;

    @Builder
    public Show(Category category, String showName, AgeRestriction ageRestriction, Place place,
                LocalDate showStartDate, LocalDate showEndDate, int runningTime,
                String notice, String posterImgUrl, String description) {
        this.category = category;
        this.showName = showName;
        this.ageRestriction = ageRestriction;
        this.place = place;
        this.showStartDate = showStartDate;
        this.showEndDate = showEndDate;
        this.runningTime = runningTime;
        this.notice = notice;
        this.posterImgUrl = posterImgUrl;
        this.description = description;
    }
}
