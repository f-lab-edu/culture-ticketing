package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_AGE_RESTRICTION;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_CATEGORY;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_PLACE_ID;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_POSTER_IMG_URL;
import static com.culture.ticketing.common.response.BaseResponseStatus.NOT_POSITIVE_SHOW_RUNNING_TIME;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "shows")
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
    @Column(name = "place_id")
    private Long placeId;

    @Builder
    public Show(Category category, String showName, AgeRestriction ageRestriction, int runningTime,
                String notice, String posterImgUrl, String description, Long placeId) {

        Objects.requireNonNull(category, EMPTY_SHOW_CATEGORY.getMessage());
        Objects.requireNonNull(ageRestriction, EMPTY_SHOW_AGE_RESTRICTION.getMessage());
        Objects.requireNonNull(placeId, EMPTY_SHOW_PLACE_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(showName), EMPTY_SHOW_NAME.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(posterImgUrl), EMPTY_SHOW_POSTER_IMG_URL.getMessage());
        Preconditions.checkArgument(runningTime > 0, NOT_POSITIVE_SHOW_RUNNING_TIME.getMessage());

        this.category = category;
        this.showName = showName;
        this.ageRestriction = ageRestriction;
        this.runningTime = runningTime;
        this.notice = notice;
        this.posterImgUrl = posterImgUrl;
        this.description = description;
        this.placeId = placeId;
    }
}
