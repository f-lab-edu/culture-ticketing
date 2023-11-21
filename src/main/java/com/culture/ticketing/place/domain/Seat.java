package com.culture.ticketing.place.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seat")
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false, updatable = false)
    private Long seatId;

    @Column(name = "coordinateX", nullable = false)
    private int coordinateX;

    @Column(name = "coordinateY", nullable = false)
    private int coordinateY;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "seatRow", nullable = false)
    private int seatRow;

    @Column(name = "seatNumber", nullable = false)
    private int seatNumber;

    @Column(name = "placeId", nullable = false)
    private int placeId;
}
