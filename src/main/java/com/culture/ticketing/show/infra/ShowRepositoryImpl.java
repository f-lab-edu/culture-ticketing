package com.culture.ticketing.show.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;
import com.querydsl.core.types.dsl.BooleanExpression;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.show.domain.QShow.show;

public class ShowRepositoryImpl extends BaseRepositoryImpl implements ShowRepositoryCustom {

    public ShowRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Show> findByShowIdGreaterThanLimitAndCategory(Long showId, int size, Category category) {

        return queryFactory.selectFrom(show)
                .where(show.showId.gt(showId),
                        categoryEq(category))
                .limit(size)
                .fetch();
    }

    private BooleanExpression categoryEq(Category category) {
        return category != null ? show.category.eq(category) : null;
    }

}
