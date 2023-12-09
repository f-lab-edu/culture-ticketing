package com.culture.ticketing.show.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.show.domain.QShow.*;

public class ShowRepositoryImpl extends BaseRepositoryImpl implements ShowRepositoryCustom {

    public ShowRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Show> findByShowIdGreaterThanLimitAndCategory(Long showId, int size, Category category) {

        return queryFactory.selectFrom(show)
                .where(show.showId.gt(showId),
                        dynamicEquals(show.category, category))
                .limit(size)
                .fetch();
    }
}
