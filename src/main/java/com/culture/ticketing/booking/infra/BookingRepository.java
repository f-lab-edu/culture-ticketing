package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.Booking;
import com.culture.ticketing.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoundIdInAndBookingStatus(Collection<Long> roundIds, BookingStatus bookingStatus);
}
