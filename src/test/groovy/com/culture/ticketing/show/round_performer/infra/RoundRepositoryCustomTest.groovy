package com.culture.ticketing.show.round_performer.infra

import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.domain.Round
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDate
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

    def "공연 아이디와 날짜로 회차 목록 조회"() {

        given:
        List<Round> rounds = [
                Round.builder()
                        .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                        .roundEndDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                        .showId(1L)
                        .build(),
                Round.builder()
                        .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 17, 0, 0))
                        .roundEndDateTime(LocalDateTime.of(2024, 1, 1, 19, 0, 0))
                        .showId(1L)
                        .build(),
                Round.builder()
                        .roundStartDateTime(LocalDateTime.of(2024, 1, 2, 10, 0, 0))
                        .roundEndDateTime(LocalDateTime.of(2024, 1, 2, 12, 0, 0))
                        .showId(1L)
                        .build(),
        ]
        roundRepository.saveAll(rounds);

        when:
        List<Round> response = roundRepository.findByShowIdAndRoundStartDate(1L, LocalDate.of(2024, 1, 1));

        then:
        response.size() == 2
    }
}
