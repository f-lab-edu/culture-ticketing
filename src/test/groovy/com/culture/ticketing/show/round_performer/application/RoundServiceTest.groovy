package com.culture.ticketing.show.round_performer.application

import com.culture.ticketing.show.PlaceFixtures
import com.culture.ticketing.show.ShowFixtures
import com.culture.ticketing.show.application.ShowService
import com.culture.ticketing.show.application.dto.PlaceResponse
import com.culture.ticketing.show.application.dto.ShowResponse
import com.culture.ticketing.show.round_performer.RoundFixtures
import com.culture.ticketing.show.round_performer.application.dto.RoundResponse
import com.culture.ticketing.show.round_performer.application.dto.RoundSaveRequest
import com.culture.ticketing.show.round_performer.domain.Round
import com.culture.ticketing.show.round_performer.exception.DuplicatedRoundDateTimeException
import com.culture.ticketing.show.round_performer.exception.OutOfRangeRoundDateTimeException
import com.culture.ticketing.show.round_performer.exception.RoundNotFoundException
import com.culture.ticketing.show.round_performer.infra.RoundRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class RoundServiceTest extends Specification {

    private RoundRepository roundRepository = Mock();
    private ShowService showService = Mock();
    private RoundService roundService = new RoundService(roundRepository, showService);

    def "회차 생성 성공"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        ShowResponse show = new ShowResponse(ShowFixtures.createShow(showId: 1L), new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)))
        showService.findShowById(1L) >> show

        Round round = request.toEntity(show);
        roundRepository.findByShowIdAndDuplicatedRoundDateTime(round.getShowId(), round.getRoundStartDateTime(), round.getRoundEndDateTime()) >> Optional.empty()

        when:
        roundService.createRound(request);

        then:
        1 * roundRepository.save(_) >> { args ->

            def savedRound = args.get(0) as Round

            savedRound.showId == 1L
            savedRound.roundStartDateTime == LocalDateTime.of(2024, 1, 1, 10, 0, 0)
            savedRound.roundEndDateTime == LocalDateTime.of(2024, 1, 1, 12, 0, 0)
        }
    }

    def "회차 생성 시 요청 값에 null 이 존재하는 경우 예외 생성"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(showId)
                .roundStartDateTime(roundStartDateTime)
                .build();

        when:
        roundService.createRound(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == expected

        where:
        showId | roundStartDateTime                     || expected
        null   | LocalDateTime.of(2024, 1, 1, 10, 0, 0) || "공연 아이디를 입력해주세요."
        1L     | null                                   || "시작 회차 일시를 입력해주세요."
    }

    def "회차 아이디로 회차 조회 시 존재하지 않는 경우 예외 발생"() {

        given:
        Long roundId = 1L;
        roundRepository.findById(roundId) >> Optional.empty()

        when:
        roundService.findById(roundId);

        then:
        def e = thrown(RoundNotFoundException.class)
        e.message == String.format("존재하지 않는 회차입니다. (roundId = %d)", roundId)
    }

    def "회차 아이디로 회차 조회"() {

        given:
        Long roundId = 1L;
        roundRepository.findById(roundId) >> Optional.of(
                RoundFixtures.createRound(
                        roundId: 1L,
                        showId: 1L,
                        roundStartDateTime: LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                        roundEndDateTime: LocalDateTime.of(2024, 1, 1, 12, 0, 0))
        )

        when:
        Round round = roundService.findById(roundId);

        then:
        round.roundId == 1L
        round.showId == 1L
        round.roundStartDateTime == LocalDateTime.of(2024, 1, 1, 10, 0, 0)
        round.roundEndDateTime == LocalDateTime.of(2024, 1, 1, 12, 0, 0)
    }

    def "공연 아이디 값으로 회차 목록 조회"() {

        given:
        roundRepository.findByShowId(1L) >> [
                RoundFixtures.createRound(roundId: 1L, showId: 1L),
                RoundFixtures.createRound(roundId: 2L, showId: 1L),
                RoundFixtures.createRound(roundId: 4L, showId: 1L)
        ]

        when:
        List<RoundResponse> foundRounds = roundService.findByShowId(1L);

        then:
        foundRounds.size() == 3
        foundRounds.collect(round -> round.roundId) == [1L, 2L, 4L]
    }

    def "회차 생성 시 중복 회차 일시 있는 경우 예외 발생"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        ShowResponse show = new ShowResponse(ShowFixtures.createShow(showId: 1L), new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)))
        showService.findShowById(1L) >> show

        Round round = request.toEntity(show);
        roundRepository.findByShowIdAndDuplicatedRoundDateTime(round.getShowId(), round.getRoundStartDateTime(), round.getRoundEndDateTime()) >> Optional.of(round)

        when:
        roundService.createRound(request);

        then:
        def e = thrown(DuplicatedRoundDateTimeException.class)
        e.message == "해당 공연에 일정이 동일한 회차가 이미 존재합니다."
    }

    def "회차 생성 시 공연 회차 일시 범위를 벗어난 경우 예외 발생"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        ShowResponse show = new ShowResponse(ShowFixtures.createShow(
                showId: 1L,
                showStartDate: LocalDate.of(2024, 1, 5),
                showEndDate: LocalDate.of(2024, 1, 20)
        ), new PlaceResponse(PlaceFixtures.createPlace(placeId: 1L)))
        showService.findShowById(1L) >> show

        when:
        roundService.createRound(request);

        then:
        def e = thrown(OutOfRangeRoundDateTimeException.class)
        e.message == "공연 가능한 회차 날짜 범위를 벗어난 입력값입니다."
    }

    def "공연 아이디 값으로 회차 응답 목록 조회"() {

        given:
        roundRepository.findByShowId(1L) >> [
                RoundFixtures.createRound(roundId: 1L, showId: 1L),
                RoundFixtures.createRound(roundId: 2L, showId: 1L),
                RoundFixtures.createRound(roundId: 3L, showId: 1L)
        ]

        when:
        List<RoundResponse> foundRoundResponses = roundService.findByShowId(1L);

        then:
        foundRoundResponses.size() == 3
        foundRoundResponses.collect(round -> round.roundId) == [1L, 2L, 3L]
    }

    def "아이디에 해당하는 회차가 존재하는지 여부 확인"() {

        given:
        Long roundId = 1L;
        roundRepository.existsById(roundId) >> true

        when:
        boolean response = roundService.notExistsById(roundId);

        then:
        !response
    }

    def "공연 아이디와 날짜로 회차 목록 조회"() {

        given:
        roundRepository.findByShowIdAndRoundStartDate(1L, LocalDate.of(2024, 1, 1)) >> [
                RoundFixtures.createRound(
                        roundId: 1L,
                        roundStartDateTime: LocalDateTime.of(2024, 1, 1, 10, 0, 0),
                        roundEndDateTime: LocalDateTime.of(2024, 1, 1, 12, 0, 0)
                ),
                RoundFixtures.createRound(
                        roundId: 2L,
                        roundStartDateTime: LocalDateTime.of(2024, 1, 1, 17, 0, 0),
                        roundEndDateTime: LocalDateTime.of(2024, 1, 1, 19, 0, 0)
                )
        ]

        when:
        List<RoundResponse> response = roundService.findByShowIdAndRoundStartDate(1L, LocalDate.of(2024, 1, 1));

        then:
        response.size() == 2
        response.roundId == [1L, 2L]
    }
}
