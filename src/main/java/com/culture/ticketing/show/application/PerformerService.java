package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.PerformerResponse;
import com.culture.ticketing.show.application.dto.PerformerSaveRequest;
import com.culture.ticketing.show.domain.Performer;
import com.culture.ticketing.show.exception.ShowNotFoundException;
import com.culture.ticketing.show.exception.ShowPerformerNotMatchException;
import com.culture.ticketing.show.infra.PerformerRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

        checkValidPerformerSaveRequest(request);

        if (showService.notExistsById(request.getShowId())) {
            throw new ShowNotFoundException(request.getShowId());
        }

        performerRepository.save(request.toEntity());
    }

    private void checkValidPerformerSaveRequest(PerformerSaveRequest request) {

        Objects.requireNonNull(request.getShowId(), "공연 아이디를 입력해주세요.");
        Preconditions.checkArgument(StringUtils.hasText(request.getPerformerName()), "출연자 이름을 입력해주세요.");
    }

    @Transactional(readOnly = true)
    public List<PerformerResponse> findPerformersByShowId(Long showId) {
        return performerRepository.findByShowId(showId).stream()
                .map(PerformerResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public void checkShowPerformersExists(Long showId, Set<Long> performerIds) {

        Set<Long> copyPerformerIds = new HashSet<>(performerIds);
        List<Performer> foundPerformers = performerRepository.findByShowIdAndPerformerIdIn(showId, performerIds);
        if (foundPerformers.size() != copyPerformerIds.size()) {
            for (Performer performer : foundPerformers) {
                copyPerformerIds.remove(performer.getPerformerId());
            }
            throw new ShowPerformerNotMatchException(copyPerformerIds.toString());
        }
    }

}
