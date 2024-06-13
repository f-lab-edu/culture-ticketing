package com.culture.ticketing.show.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.domain.ShowFilter;
import com.culture.ticketing.show.domain.ShowOrderBy;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.culture.ticketing.show.domain.QShow.show;

public class ShowRepositoryImpl extends BaseRepositoryImpl implements ShowRepositoryCustom {

    public ShowRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Show> searchShowsWithPaging(Long showId, int size, ShowFilter showFilter, ShowOrderBy orderBy) {

        return queryFactory.selectFrom(show)
                .where(show.showId.gt(showId), showFilter.getShowFilter())
                .limit(size)
                .orderBy(orderBy.getOrderSpecifier())
                .fetch();
    }

    @Override
    public List<Show> findByBookingStartDateTimeLeftAnHour(LocalDateTime now) {

        return queryFactory
                .selectFrom(show)
                .where(show.bookingStartDateTime.goe(now.plusHours(1).withSecond(0).withNano(0))
                        .and(show.bookingStartDateTime.lt(now.plusHours(1).plusMinutes(1).withSecond(0).withNano(0))))
                .fetch();
    }
}
