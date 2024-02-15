package com.culture.ticketing.booking.domain;

import com.culture.ticketing.common.entity.BaseEntity;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false, updatable = false)
    private Long bookingId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "round_id", nullable = false)
    private Long roundId;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<BookingShowSeat> bookingShowSeats = new HashSet<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<BookingShowFloor> bookingShowFloors = new HashSet<>();

    @Builder
    public Booking(Long bookingId, Long userId, Long roundId, int totalPrice , BookingStatus bookingStatus) {

        Objects.requireNonNull(userId, "유저 아이디를 입력해주세요.");
        Objects.requireNonNull(roundId, "회차 아이디를 입력해주세요.");
        Objects.requireNonNull(bookingStatus, "예약 상태를 입력해주세요.");
        Preconditions.checkArgument(totalPrice >= 0, "총 가격은 0 이상 숫자로 입력해주세요.");

        this.bookingId = bookingId;
        this.userId = userId;
        this.roundId = roundId;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    public void setBookingShowSeats(Set<BookingShowSeat> bookingShowSeats) {
        this.bookingShowSeats = bookingShowSeats;
    }

    public void setBookingShowFloors(Set<BookingShowFloor> bookingShowFloors) {
        this.bookingShowFloors = bookingShowFloors;
    }
}
