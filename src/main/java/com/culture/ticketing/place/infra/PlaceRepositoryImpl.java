package com.culture.ticketing.place.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.place.domain.Place;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.place.domain.QPlace.place;

public class PlaceRepositoryImpl extends BaseRepositoryImpl implements PlaceRepositoryCustom {

    public PlaceRepositoryImpl(EntityManager em) {
        super(em);
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
