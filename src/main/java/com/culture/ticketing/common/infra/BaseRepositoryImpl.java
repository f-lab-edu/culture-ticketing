package com.culture.ticketing.common.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl {

    protected final EntityManager em;
    protected final JPAQueryFactory queryFactory;

    public BaseRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
}
