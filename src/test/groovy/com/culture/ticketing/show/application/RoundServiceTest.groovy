package com.culture.ticketing.show.application

import com.culture.ticketing.show.RoundFixtures
import com.culture.ticketing.show.application.dto.RoundSaveRequest
import com.culture.ticketing.show.domain.AgeRestriction
import com.culture.ticketing.show.domain.Category
import com.culture.ticketing.show.domain.Round
import com.culture.ticketing.show.domain.Show
import com.culture.ticketing.show.exception.DuplicatedRoundDateTimeException
import com.culture.ticketing.show.exception.OutOfRangeRoundDateTimeException
import com.culture.ticketing.show.exception.RoundNotFoundException
import com.culture.ticketing.show.infra.RoundRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class RoundServiceTest extends Specification {

    @SpringBean
    private RoundRepository roundRepository = Mock();
    @SpringBean
    private ShowService showService = Mock();
    private RoundService roundService = new RoundService(roundRepository, showService);

    def "회차 생성 성공"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        Show show = Show.builder()
                .showId(1L)
                .category(Category.CONCERT)
                .ageRestriction(AgeRestriction.ALL)
                .placeId(1L)
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 2, 20))
                .showName("테스트 공연")
                .posterImgUrl("http://abc.jpg")
                .runningTime(120)
                .build();

        Round round = request.toEntity(show);

        showService.findShowById(1L) >> show
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

    def "회차 생성 시 공연 아이디가 null 인 경우 예외 생성"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(null)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        when:
        roundService.createRound(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "회차 생성 시 회차 시작 일시가 null 인 경우 예외 생성"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(null)
                .build();

        when:
        roundService.createRound(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "시작 회차 일시를 입력해주세요."
    }

    def "회차 아이디로 회차 조회 시 존재하지 않는 경우 예외 발생"() {

        given:
        roundRepository.findById(1L) >> Optional.empty()

        when:
        roundService.findById(1L);

        then:
        def e = thrown(RoundNotFoundException.class)
        e.message == "존재하지 않는 회차입니다. (roundId = 1)"
    }

    def "공연 아이디 값으로 회차 목록 조회"() {

        given:
        List<Round> rounds = List.of(
                RoundFixtures.createRound(1L, 1L),
                RoundFixtures.createRound(2L, 1L),
                RoundFixtures.createRound(3L, 2L),
                RoundFixtures.createRound(4L, 1L),
                RoundFixtures.createRound(5L, 3L)
        );
        roundRepository.findByShowId(1L) >> List.of(rounds.get(0), rounds.get(1), rounds.get(3))

        when:
        List<Round> foundRounds = roundService.findByShowId(1L);

        then:
        foundRounds.collect(round -> round.showId == 1L).size() == 3
        foundRounds.collect(round -> round.roundId) == [1L, 2L, 4L]

    }

    def "회차 생성 시 중복 회차 일시 있는 경우 예외 발생"() {

        given:
        RoundSaveRequest request = RoundSaveRequest.builder()
                .showId(1L)
                .roundStartDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        Show show = Show.builder()
                .showId(1L)
                .category(Category.CONCERT)
                .ageRestriction(AgeRestriction.ALL)
                .placeId(1L)
                .showStartDate(LocalDate.of(2024, 1, 1))
                .showEndDate(LocalDate.of(2024, 2, 20))
                .showName("테스트 공연")
                .posterImgUrl("http://abc.jpg")
                .runningTime(120)
                .build();

        Round round = request.toEntity(show);

        showService.findShowById(1L) >> show
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

        Show show = Show.builder()
                .showId(1L)
                .category(Category.CONCERT)
                .ageRestriction(AgeRestriction.ALL)
                .placeId(1L)
                .showStartDate(LocalDate.of(2024, 1, 5))
                .showEndDate(LocalDate.of(2024, 2, 20))
                .showName("테스트 공연")
                .posterImgUrl("http://abc.jpg")
                .runningTime(120)
                .build();

        showService.findShowById(1L) >> show

        when:
        roundService.createRound(request);

        then:
        def e = thrown(OutOfRangeRoundDateTimeException.class)
        e.message == "공연 가능한 회차 날짜 범위를 벗어난 입력값입니다."
    }
}