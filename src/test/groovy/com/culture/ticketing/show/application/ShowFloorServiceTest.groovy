package com.culture.ticketing.show.application

import com.culture.ticketing.show.application.dto.ShowFloorSaveRequest
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException
import com.culture.ticketing.show.infra.ShowFloorRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ShowFloorServiceTest extends Specification {

    @SpringBean
    private ShowFloorRepository showFloorRepository = Mock();
    @SpringBean
    private ShowSeatGradeService showSeatGradeService = Mock();
    private ShowFloorService showFloorService = new ShowFloorService(showFloorRepository, showSeatGradeService);

    def "공연 플로어 생성 성공"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("F1")
                .count(700)
                .build();
        showSeatGradeService.notExistsById(1L) >> false

        when:
        showFloorService.createShowFloor(request);

        then:
        1 * showFloorRepository.save(_)
    }

    def "공연 플로어 생성 시 공연 좌석 등급 아이디 값이 null 인 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(null)
                .showFloorName("F1")
                .count(700)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 플로어 구역명이 null 인 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName(null)
                .count(700)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 구역명을 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 플로어 구역명이 빈 값인 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("")
                .count(700)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 구역명을 입력해주세요."
    }

    def "공연 플로어 생성 시 인원수가 0 이하 인 경우 예외 발생"() {

        given:
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(1L)
                .showFloorName("F1")
                .count(0)
                .build();

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "공연 플로어 인원수를 1 이상 숫자로 입력해주세요."
    }

    def "공연 플로어 생성 시 공연 좌석 등급 아이디 값에 해당하는 공연 좌석 등급이 존재하지 않을 경우 예외 발생"() {

        given:
        Long showSeatGradeId = 1L;
        ShowFloorSaveRequest request = ShowFloorSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .showFloorName("F1")
                .count(700)
                .build();
        showSeatGradeService.notExistsById(showSeatGradeId) >> true

        when:
        showFloorService.createShowFloor(request);

        then:
        def e = thrown(ShowSeatGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 좌석 등급입니다. (showSeatGradeId = %d)", showSeatGradeId)
    }
}