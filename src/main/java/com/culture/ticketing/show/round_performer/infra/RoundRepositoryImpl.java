package com.culture.ticketing.show.round_performer.infra;

import com.culture.ticketing.common.infra.BaseRepositoryImpl;
import com.culture.ticketing.show.round_performer.domain.Round;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.culture.ticketing.show.domain.QRound.round;


public class RoundRepositoryImpl extends BaseRepositoryImpl implements RoundRepositoryCustom {

    public RoundRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<Round> findByShowIdAndDuplicatedRoundDateTime(Long showId, LocalDateTime roundStartDateTime, LocalDateTime roundEndDateTime) {

        return Optional.ofNullable(queryFactory
                .selectFrom(round)
                .where(
                        round.showId.eq(showId),
                        round.roundStartDateTime.between(roundStartDateTime, roundEndDateTime)
                                .or(round.roundEndDateTime.between(roundStartDateTime, roundEndDateTime))
                                .or(round.roundStartDateTime.loe(roundStartDateTime)
                                        .and(round.roundEndDateTime.goe(roundEndDateTime)))
                )
                .fetchOne());
    }

    @Override
    public List<Round> findByShowIdAndRoundStartDate(Long showId, LocalDate roundStartDate) {

        return queryFactory
                .selectFrom(round)
                .where(
                        round.showId.eq(showId),
                        round.roundStartDateTime.between(roundStartDate.atTime(0, 0, 0), roundStartDate.atTime(23, 59, 59))
                )
                .fetch();
    }
}
