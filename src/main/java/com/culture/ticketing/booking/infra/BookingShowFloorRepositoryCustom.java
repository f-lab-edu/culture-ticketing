package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingShowFloor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookingShowFloorRepositoryCustom {

    boolean existsAlreadyBookingShowFloorsInRound(Set<BookingShowFloorSaveRequest> showFloors, Long roundId);

    List<BookingShowFloor> findSuccessBookingShowFloorsByRoundIdIn(Collection<Long> roundIds);
}
