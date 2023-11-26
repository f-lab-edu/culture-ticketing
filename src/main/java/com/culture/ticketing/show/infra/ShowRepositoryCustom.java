package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Show;

import java.util.List;

public interface ShowRepositoryCustom {

    List<Show> findByShowIdGreaterThanLimit(Long showId, int size);
}
