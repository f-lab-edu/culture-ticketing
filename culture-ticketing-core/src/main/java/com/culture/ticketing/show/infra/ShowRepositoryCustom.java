package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepositoryCustom {

    List<Show> findByShowIdGreaterThanLimitAndCategory(Long showId, int size, Category category);

    List<Show> findByBookingStartDateTimeLeftAnHour(LocalDateTime now, int pageSize, long offset);
}
