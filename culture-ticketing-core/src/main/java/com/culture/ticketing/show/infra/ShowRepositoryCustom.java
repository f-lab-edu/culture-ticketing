package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.Category;
import com.culture.ticketing.show.domain.Show;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepositoryCustom {

    List<Show> searchShowsWithPaging(Long showId, int size, Category category, String showName);

    List<Show> findByBookingStartDateTimeLeftAnHour(LocalDateTime now);
}
