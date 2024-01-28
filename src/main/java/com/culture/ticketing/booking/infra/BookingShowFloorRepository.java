package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingShowFloorRepository extends JpaRepository<BookingShowFloor, Long> {

    List<BookingShowFloor> findByBookingIdIn(Collection<Long> bookingIds);
}
