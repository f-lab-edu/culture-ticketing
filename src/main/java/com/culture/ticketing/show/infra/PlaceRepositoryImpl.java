package com.culture.ticketing.show.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.domain.Place;

import javax.persistence.EntityManager;
import java.util.List;

import static com.culture.ticketing.show.domain.QPlace.place;

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
