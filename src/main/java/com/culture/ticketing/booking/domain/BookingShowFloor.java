package com.culture.ticketing.booking.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "booking_show_floor")
public class BookingShowFloor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_show_floor_id", nullable = false, updatable = false)
    private Long bookingShowFloorId;

    @Column(name = "show_floor_id", nullable = false)
    private Long showFloorId;

    @Column(name = "entry_order", nullable = false)
    private int entryOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Builder
    public BookingShowFloor(Long bookingShowFloorId, Long showFloorId, int entryOrder, Booking booking) {

        Objects.requireNonNull(booking, "예약 정보를 입력해주세요.");
        Objects.requireNonNull(showFloorId, "공연 플로어 아이디를 입력새주세요.");
        Preconditions.checkArgument(entryOrder > 0, "플로어 입장 번호는 1 이상 숫자로 입력해주세요.");

        this.bookingShowFloorId = bookingShowFloorId;
        this.showFloorId = showFloorId;
        this.entryOrder = entryOrder;
        this.booking = booking;
    }
}
