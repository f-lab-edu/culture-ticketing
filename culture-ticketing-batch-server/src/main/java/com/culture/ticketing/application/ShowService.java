package com.culture.ticketing.application;

import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Transactional(readOnly = true)
    public List<Show> findByBookingStartDateTimeLeftAnHour(LocalDateTime now) {

        return showRepository.findByBookingStartDateTimeLeftAnHour(now);
    }
}
