package com.culture.ticketing.place.infra;

import com.culture.ticketing.place.domain.Place;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.place.domain.QPlace.*;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PlaceRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Place> findByPlaceIdGreaterThanLimit(Long placeId, int size) {

        return queryFactory
                .selectFrom(place)
                .where(place.placeId.gt(placeId))
                .limit(size)
                .fetch();
    }
}
