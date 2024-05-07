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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_area")
public class ShowArea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_area_id", nullable = false, updatable = false)
    private Long showAreaId;

    @Column(name = "show_area_name", nullable = false)
    private String showAreaName;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Column(name = "show_area_grade_id", nullable = false)
    private Long showAreaGradeId;

    @Builder
    public ShowArea(Long showAreaId, String showAreaName, Long showId, Long showAreaGradeId) {

        Objects.requireNonNull(showId, "공연 아이디를 입력해주세요.");
        Objects.requireNonNull(showAreaGradeId, "공연 구역 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(showAreaName), "공연 구역명을 입력해주세요.");

        this.showAreaId = showAreaId;
        this.showAreaName = showAreaName;
        this.showId = showId;
        this.showAreaGradeId = showAreaGradeId;
    }
}
