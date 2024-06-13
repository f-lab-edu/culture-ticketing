package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.domain.ShowFilter;
import com.culture.ticketing.show.domain.ShowOrderBy;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepositoryCustom {

    List<Show> searchShowsWithPaging(Long showId, int size, ShowFilter showFilter, ShowOrderBy orderBy);

    List<Show> findByBookingStartDateTimeLeftAnHour(LocalDateTime now);
}
