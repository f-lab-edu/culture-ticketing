package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingShowFloorRepository extends JpaRepository<BookingShowFloor, Long>, BookingShowFloorRepositoryCustom {

    List<BookingShowFloor> findByBooking_RoundIdInAndBooking_BookingStatus(Collection<Long> roundIds, BookingStatus bookingStatus);
}
