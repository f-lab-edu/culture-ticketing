package com.culture.ticketing.booking.domain;

import com.culture.ticketing.common.entity.BaseEntity;
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
@Table(name = "booking_show_seat")
public class BookingShowSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_show_seat_id", nullable = false, updatable = false)
    private Long bookingShowSeatId;

    @Column(name = "show_seat_id", nullable = false)
    private Long showSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Builder
    public BookingShowSeat(Long bookingShowSeatId, Long showSeatId, Booking booking) {

        Objects.requireNonNull(booking, "예약 정보를 입력해주세요.");
        Objects.requireNonNull(showSeatId, "공연 좌석 아이디를 입력해주세요.");

        this.bookingShowSeatId = bookingShowSeatId;
        this.showSeatId = showSeatId;
        this.booking = booking;
    }
}
