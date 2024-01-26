package com.culture.ticketing.show.infra

import com.culture.ticketing.show.domain.Round
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoundRepositoryCustomTest extends Specification {

    @Autowired
    private RoundRepository roundRepository;

    def "회차 중 같은 공연 일시 겹치는 회차 조회"() {

        given:
        Round round = Round.builder()
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .roundEndDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .showId(1L)
                .build();
        roundRepository.save(round);

        when:
        Optional<Round> foundRound = roundRepository.findByShowIdAndDuplicatedRoundDateTime(1L,
                LocalDateTime.of(2024, 1, 1, 12, 0, 0),
                LocalDateTime.of(2024, 1, 1, 14, 0, 0)
        );

        then:
        foundRound.isPresent()
    }
}
