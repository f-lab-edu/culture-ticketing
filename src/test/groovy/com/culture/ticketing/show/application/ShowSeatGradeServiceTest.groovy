package com.culture.ticketing.show.application

import com.culture.ticketing.show.ShowSeatGradeFixtures
import com.culture.ticketing.show.application.dto.ShowSeatGradeResponse
import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest
import com.culture.ticketing.show.domain.ShowSeatGrade
import com.culture.ticketing.show.exception.ShowNotFoundException
import com.culture.ticketing.show.infra.ShowSeatGradeRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ShowSeatGradeServiceTest extends Specification {

    @SpringBean
    private ShowSeatGradeRepository showSeatGradeRepository = Mock();
    @SpringBean
    private ShowService showService = Mock();
    private ShowSeatGradeService showSeatGradeService = new ShowSeatGradeService(showSeatGradeRepository, showService);

    def "공연 좌석 등급 생성 성공"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("VIP")
                .price(100000)
                .showId(1L)
                .build();
        showService.notExistsById(1L) >> false

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        1 * showSeatGradeRepository.save(_)
    }

    def "공연 좌석 등급 생성 시 공연 아이디 값이 null 인 경우 예외 발생"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("VIP")
                .price(100000)
                .showId(null)
                .build();

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 아이디를 입력해주세요."
    }

    def "공연 좌석 등급 생성 시 좌석 등급 명이 null 인 경우 예외 발생"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade(null)
                .price(100000)
                .showId(1L)
                .build();

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 등급을 입력해주세요."
    }

    def "공연 좌석 등급 생성 시 좌석 등급 명이 빈 값인 경우 예외 발생"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("")
                .price(100000)
                .showId(1L)
                .build();

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 등급을 입력해주세요."
    }

    def "공연 좌석 등급 생성 시 가격이 0 미만 인 경우 예외 발생"() {

        given:
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("VIP")
                .price(-1)
                .showId(1L)
                .build();

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 좌석 가격을 0 이상으로 입력해주세요."
    }

    def "공연 좌석 등급 생성 시 공연 아이디 값에 해당하는 공연이 존재하지 않는 경우 예외 발생"() {

        given:
        Long showId = 1L;
        ShowSeatGradeSaveRequest request = ShowSeatGradeSaveRequest.builder()
                .seatGrade("VIP")
                .price(100000)
                .showId(showId)
                .build();
        showService.notExistsById(showId) >> true

        when:
        showSeatGradeService.createShowSeatGrade(request);

        then:
        def e = thrown(ShowNotFoundException.class)
        e.message == String.format("존재하지 않는 공연입니다. (showId = %d)", showId)
    }

    def "공연 좌석 등급 아이디 값에 해당하는 공연 좌석 등급 존재 여부 확인"() {

        given:
        Long showSeatGradeId = 1L;
        showSeatGradeRepository.existsById(showSeatGradeId) >> true

        when:
        boolean response = showSeatGradeService.notExistsById(showSeatGradeId);

        then:
        !response
    }

    def "공연_아이디로_공연_좌석_등급_목록_조회"() {

        given:
        List<ShowSeatGrade> showSeatGrades = List.of(
                ShowSeatGradeFixtures.createShowSeatGrade(1L),
                ShowSeatGradeFixtures.createShowSeatGrade(2L),
                ShowSeatGradeFixtures.createShowSeatGrade(3L)
        );
        showSeatGradeRepository.findByShowId(1L) >> showSeatGrades;

        when:
        List<ShowSeatGradeResponse> response = showSeatGradeService.findShowSeatGradesByShowId(1L);

        then:
        response.size() == 3
    }
}
