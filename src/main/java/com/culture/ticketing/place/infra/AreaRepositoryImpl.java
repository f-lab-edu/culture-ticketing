package com.culture.ticketing.place.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.place.domain.Area;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.place.domain.QArea.area;
import static com.culture.ticketing.place.domain.QSeat.seat;
import static com.culture.ticketing.show.show_seat.domain.QShowSeat.showSeat;
import static com.culture.ticketing.show.show_seat.domain.QShowSeatGrade.showSeatGrade;

public class AreaRepositoryImpl extends BaseRepositoryImpl implements AreaRepositoryCustom {

    public AreaRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Area> findByShowId(Long showId) {
        return queryFactory
                .selectFrom(area)
                .join(seat)
                .join(showSeat)
                .join(showSeatGrade)
                .where(showSeatGrade.showId.eq(showId))
                .groupBy(area.areaId)
                .fetch();
    }
}
