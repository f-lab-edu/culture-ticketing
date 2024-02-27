package com.culture.ticketing.show.show_seat.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.show_seat.domain.ShowSeat;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.place.domain.QArea.area;
import static com.culture.ticketing.place.domain.QSeat.seat;
import static com.culture.ticketing.show.show_seat.domain.QShowSeat.showSeat;

public class ShowSeatRepositoryImpl extends BaseRepositoryImpl implements ShowSeatRepositoryCustom {

    public ShowSeatRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<ShowSeat> findByAreaId(Long areaId) {
        return queryFactory
                .selectFrom(showSeat)
                .join(seat).on(seat.seatId.eq(showSeat.seatId))
                .join(area).on(area.areaId.eq(seat.areaId))
                .where(area.areaId.eq(areaId))
                .fetch();
    }
}
