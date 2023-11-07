package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
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

}
