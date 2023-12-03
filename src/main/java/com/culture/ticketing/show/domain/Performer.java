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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PERFORMER_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "performer")
public class Performer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performer_id", nullable = false, updatable = false)
    private Long performerId;
    @Column(name = "performer_name", nullable = false)
    private String performerName;
    @Column(name = "performer_img_url")
    private String performerImgUrl;
    @Column(name = "role")
    private String role;
    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Builder
    public Performer(String performerName, String performerImgUrl, String role, Long showId) {

        Objects.requireNonNull(showId, EMPTY_SHOW_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(performerName), EMPTY_PERFORMER_NAME.getMessage());

        this.performerName = performerName;
        this.performerImgUrl = performerImgUrl;
        this.role = role;
        this.showId = showId;
    }
}
