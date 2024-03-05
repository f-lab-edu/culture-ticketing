package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.domain.BookingShowSeat;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.common.infra.BaseRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static com.culture.ticketing.booking.domain.QBookingShowSeat.bookingShowSeat;

public class BookingShowSeatRepositoryImpl extends BaseRepositoryImpl implements BookingShowSeatRepositoryCustom {

    public BookingShowSeatRepositoryImpl(EntityManager em) {
        super(em);
    }


    @Override
    public List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdAndShowSeatIds(Long roundId, Collection<Long> showSeatIds) {
        return queryFactory
                .selectFrom(bookingShowSeat)
                .where(
                        bookingShowSeat.showSeatId.in(showSeatIds),
                        bookingShowSeat.booking.roundId.eq(roundId),
                        bookingShowSeat.booking.bookingStatus.eq(BookingStatus.SUCCESS)
                )
                .fetch();
    }

    @Override
    public List<BookingShowSeat> findSuccessBookingShowSeatsByRoundIdIn(Collection<Long> roundIds) {
        return queryFactory
                .selectFrom(bookingShowSeat)
                .where(
                        bookingShowSeat.booking.roundId.in(roundIds),
                        bookingShowSeat.booking.bookingStatus.eq(BookingStatus.SUCCESS)
                )
                .fetch();
    }
}
