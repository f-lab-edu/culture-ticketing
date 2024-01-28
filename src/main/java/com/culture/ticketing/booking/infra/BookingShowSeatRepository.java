package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingShowSeatRepository extends JpaRepository<BookingShowSeat, Long> {

    List<BookingShowSeat> findByBookingIdIn(Collection<Long> bookingIds);
}
