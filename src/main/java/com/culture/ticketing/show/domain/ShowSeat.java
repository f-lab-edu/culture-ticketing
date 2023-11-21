package com.culture.ticketing.show.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "show_seat")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_seat_id", nullable = false, updatable = false)
    private Long showSeatId;

    @Column(name = "show_seat_grade_id", nullable = false)
    private Long showSeatGradeId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @ColumnDefault("false")
    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;
}
