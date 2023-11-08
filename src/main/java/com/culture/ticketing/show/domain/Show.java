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
    private Category category;
    private String showName;
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;
    private LocalDate showStartDate;
    private LocalDate showEndDate;
    private int runningTime;
    private String notice;
    private String posterImgUrl;
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
