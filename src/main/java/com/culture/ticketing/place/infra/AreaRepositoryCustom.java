package com.culture.ticketing.place.infra;

import com.culture.ticketing.place.domain.Area;

import java.util.List;

public interface AreaRepositoryCustom {

    List<Area> findByShowId(Long showId);
}
