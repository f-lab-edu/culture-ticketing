package com.culture.ticketing.show.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.domain.ShowSchedule;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.Expressions;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.culture.ticketing.show.domain.QShowSchedule.showSchedule;

public class ShowScheduleRepositoryImpl extends BaseRepositoryImpl implements ShowScheduleRepositoryCustom {

    public ShowScheduleRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<ShowSchedule> findByShowAndDuplicatedShowScheduleDateTime(Show show, LocalDateTime showScheduleDateTime) {


        return Optional.ofNullable(queryFactory
                .selectFrom(showSchedule)
                .where(
                        showSchedule.showId.eq(show.getShowId()),
                        showSchedule.showScheduleDateTime.loe(showScheduleDateTime),
                        showSchedule.showScheduleDateTime.goe(showScheduleDateTime.minusMinutes(show.getRunningTime()))
                        )
                .fetchOne());
    }
}
