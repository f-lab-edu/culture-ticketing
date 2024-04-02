package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowLikeRepository extends JpaRepository<ShowLike, Long> {

    Optional<ShowLike> findByUserIdAndShowId(Long userId, Long showId);

    List<ShowLike> findByShowIdIn(Collection<Long> showIds);
}
