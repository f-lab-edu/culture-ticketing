package com.culture.ticketing.show.application

import com.culture.ticketing.show.application.dto.ShowSeatGradeSaveRequest
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

    def "공연_좌석_등급_생성_시_공연_아이디_값이_null_인_경우_예외_발생"() {

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

    def "공연_좌석_등급_생성_시_좌석_등급_명이_null_인_경우_예외_발생"() {

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

    def "공연_좌석_등급_생성_시_좌석_등급_명이_빈_값_인_경우_예외_발생"() {

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

    def "공연_좌석_등급_생성_시_가격이_0미만_인_경우_예외_발생"() {

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

    def "공연_좌석_등급_생성_시_공연_아이디_값에_해당하는_공연이_존재하지_않는_경우_예외_발생"() {

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

    def "공연_좌석_등급_아이디_값에_해당하는_공연_좌석_등급_존재_여부_확인"() {

        given:
        Long showSeatGradeId = 1L;
        showSeatGradeRepository.existsById(showSeatGradeId) >> true

        when:
        boolean response = showSeatGradeService.notExistsById(showSeatGradeId);

        then:
        !response
    }
}
