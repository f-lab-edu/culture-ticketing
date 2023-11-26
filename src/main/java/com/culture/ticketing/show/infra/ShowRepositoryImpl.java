package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Show;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.show.domain.QShow.*;

public class ShowRepositoryImpl implements ShowRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ShowRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Show> findByShowIdGreaterThanLimit(Long showId, int size) {

        return queryFactory.selectFrom(show)
                .where(show.showId.gt(showId))
                .limit(size)
                .fetch();
    }
}
