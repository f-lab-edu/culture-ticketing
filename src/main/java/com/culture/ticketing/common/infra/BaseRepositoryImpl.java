package com.culture.ticketing.common.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl {

    protected final EntityManager em;
    protected final JPAQueryFactory queryFactory;

    public BaseRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public static <T> BooleanExpression dynamicEquals(SimpleExpression<T> expression, T object) {
        return object != null ? expression.eq(object) : null;
    }
}
