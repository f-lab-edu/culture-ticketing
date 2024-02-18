package com.culture.ticketing.show.domain;

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
@Table(name = "show_floor")
public class ShowFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_floor_id", nullable = false, updatable = false)
    private Long showFloorId;

    @Column(name = "show_floor_name", nullable = false)
    private String showFloorName;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "show_floor_grade_id", nullable = false)
    private Long showFloorGradeId;

    @Builder
    public ShowFloor(Long showFloorId, String showFloorName, int count, Long showFloorGradeId) {

        Objects.requireNonNull(showFloorGradeId, "공연 플로어 등급 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(showFloorName), "공연 플로어 구역명을 입력해주세요.");
        Preconditions.checkArgument(count > 0, "공연 플로어 인원수를 1 이상 숫자로 입력해주세요.");

        this.showFloorId = showFloorId;
        this.showFloorName = showFloorName;
        this.count = count;
        this.showFloorGradeId = showFloorGradeId;
    }
}
