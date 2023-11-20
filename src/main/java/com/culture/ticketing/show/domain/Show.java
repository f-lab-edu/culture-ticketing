package com.culture.ticketing.show.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

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

        if (category == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_CATEGORY);
        }
        if (ageRestriction == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_AGE_RESTRICTION);
        }
        if (!StringUtils.hasText(showName)) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_NAME);
        }
        if (runningTime <= 0) {
            throw new BaseException(BaseResponseStatus.NOT_POSITIVE_SHOW_RUNNING_TIME);
        }
        if (!StringUtils.hasText(posterImgUrl)) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_POSTER_IMG_URL);
        }
        if (placeId == null) {
            throw new BaseException(BaseResponseStatus.EMPTY_SHOW_PLACE_ID);
        }

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
