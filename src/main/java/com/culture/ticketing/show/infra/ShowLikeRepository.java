package com.culture.ticketing.show.infra;

import com.culture.ticketing.show.domain.ShowLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowLikeRepository extends JpaRepository<ShowLike, Long> {

    Optional<ShowLike> findByUserIdAndShowId(Long userId, Long showId);
}
