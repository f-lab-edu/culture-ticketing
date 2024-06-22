package com.culture.ticketing.infra;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowRepositoryItemReader extends AbstractPagingItemReader<Show> {

    private final ShowRepository showRepository;
    private final LocalDateTime localDateTime;

    public ShowRepositoryItemReader(ShowRepository showRepository, LocalDateTime localDateTime, int pageSize) {
        this.showRepository = showRepository;
        this.localDateTime = localDateTime;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {

        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        List<Show> shows = showRepository.findByBookingStartDateTimeLeftAnHour(localDateTime, getPageSize(), getPage());

        results.addAll(shows);
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }
}
