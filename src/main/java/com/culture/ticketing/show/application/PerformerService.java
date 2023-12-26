package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.PerformerSaveRequest;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.infra.PerformerRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPerformerName()), "출연자 이름을 입력해주세요.");

        if (!showService.existsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        Performer performer = request.toEntity();
        performerRepository.save(performer);
    }

    @Transactional(readOnly = true)
    public List<PerformerResponse> findPerformersByShowId(Long showId) {
        return performerRepository.findByShowId(showId).stream()
                .map(PerformerResponse::new)
                .collect(Collectors.toList());
    }
}
