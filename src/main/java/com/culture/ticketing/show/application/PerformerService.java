package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PerformerSaveRequest;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.PerformerRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_PERFORMER_NAME;
import static com.culture.ticketing.common.response.BaseResponseStatus.EMPTY_SHOW_ID;

@Service
public class PerformerService {

    private final PerformerRepository performerRepository;
    private final ShowService showService;

    public PerformerService(PerformerRepository performerRepository, ShowService showService) {
        this.performerRepository = performerRepository;
        this.showService = showService;
    }

    @Transactional
    public void createPerformer(PerformerSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), EMPTY_SHOW_ID.getMessage());
        Preconditions.checkArgument(StringUtils.hasText(request.getPerformerName()), EMPTY_PERFORMER_NAME.getMessage());

        if (!showService.existsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        Performer performer = request.toEntity();
        performerRepository.save(performer);
    }
}
