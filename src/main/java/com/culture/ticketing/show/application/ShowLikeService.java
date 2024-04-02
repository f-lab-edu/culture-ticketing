package com.culture.ticketing.show.application;

import com.culture.ticketing.show.application.dto.ShowResponse;
import com.culture.ticketing.show.domain.ShowLike;
import com.culture.ticketing.show.infra.ShowLikeRepository;
import com.culture.ticketing.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ShowLikeService {

    private final ShowLikeRepository showLikeRepository;
    private final ShowService showService;

    public ShowLikeService(ShowLikeRepository showLikeRepository, ShowService showService) {
        this.showLikeRepository = showLikeRepository;
        this.showService = showService;
    }

    @Transactional
    public void createShowLike(User user, Long showId) {

        ShowResponse show = showService.findShowById(showId);

        ShowLike showLike = showLikeRepository.findByUserIdAndShowId(user.getUserId(), showId)
                .orElseGet(() -> ShowLike.builder()
                        .userId(user.getUserId())
                        .showId(show.getShowId())
                        .build());

        showLikeRepository.save(showLike);
    }

    @Transactional
    public void deleteShowLike(User user, Long showId) {

        showLikeRepository.findByUserIdAndShowId(user.getUserId(), showId)
                .ifPresent(showLikeRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<ShowLike> findByShowIds(Collection<Long> showIds) {

        return showLikeRepository.findByShowIdIn(showIds);
    }
}
