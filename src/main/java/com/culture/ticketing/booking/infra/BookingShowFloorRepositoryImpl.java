package com.culture.ticketing.booking.infra;

import com.culture.ticketing.booking.application.dto.BookingShowFloorSaveRequest;
import com.culture.ticketing.booking.domain.BookingShowFloor;
import com.culture.ticketing.booking.domain.BookingStatus;
import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.culture.ticketing.booking.domain.QBookingShowFloor.bookingShowFloor;

public class BookingShowFloorRepositoryImpl extends BaseRepositoryImpl implements BookingShowFloorRepositoryCustom {

    BookingShowFloorRepositoryImpl(EntityManager em) {
        super(em);
    }


    @Override
    public List<BookingShowFloor> findSuccessBookingShowFloorsByRoundIdIn(Collection<Long> roundIds) {
        return queryFactory
                .selectFrom(bookingShowFloor)
                .where(
                        bookingShowFloor.booking.roundId.in(roundIds),
                        bookingShowFloor.booking.bookingStatus.eq(BookingStatus.SUCCESS)
                )
                .fetch();
    }

    @Override
    public boolean existsAlreadyBookingShowFloorsInRound(Set<BookingShowFloorSaveRequest> showFloors, Long roundId) {
        return queryFactory
                .selectFrom(bookingShowFloor)
                .where(
                        Expressions.list(bookingShowFloor.showFloorId, bookingShowFloor.entryOrder).in(searchShowFloorsIn(showFloors)),
                        bookingShowFloor.booking.roundId.eq(roundId),
                        bookingShowFloor.booking.bookingStatus.eq(BookingStatus.SUCCESS)
                )
                .fetch().size() > 0;
    }

    private Expression[] searchShowFloorsIn(Collection<BookingShowFloorSaveRequest> showFloors) {

        List<Expression<Object>> tuples = new ArrayList<>();

        for (BookingShowFloorSaveRequest showFloor: showFloors) {
            tuples.add(Expressions.template(Object.class, "(({0}, {1}))", showFloor.getShowFloorId(), showFloor.getEntryOrder()));
        }

        return tuples.toArray(new Expression[0]);
    }


}
