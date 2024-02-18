package com.culture.ticketing.show.show_floor.domain;

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
@Table(name = "show_floor_grade")
public class ShowFloorGrade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_floor_grade_id", nullable = false, updatable = false)
    private Long showFloorGradeId;

    @Column(name = "show_floor_grade_name", nullable = false)
    private String showFloorGradeName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    @Builder
    public ShowFloorGrade(Long showFloorGradeId, String showFloorGradeName, int price, Long showId) {

        Objects.requireNonNull(showId, "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(showFloorGradeName), "공연 플로어 등급명을 입력해주세요.");
        Preconditions.checkArgument(price >= 0, "공연 플로어 가격을 0 이상으로 입력해주세요.");

        this.showFloorGradeId = showFloorGradeId;
        this.showFloorGradeName = showFloorGradeName;
        this.price = price;
        this.showId = showId;
    }
}
