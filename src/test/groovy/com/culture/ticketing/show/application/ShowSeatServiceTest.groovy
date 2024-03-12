package com.culture.ticketing.show.application

import com.culture.ticketing.show.ShowAreaFixtures
import com.culture.ticketing.show.ShowAreaGradeFixtures
import com.culture.ticketing.show.ShowSeatFixtures
import com.culture.ticketing.show.application.dto.ShowAreaGradeResponse
import com.culture.ticketing.show.application.dto.ShowAreaResponse
import com.culture.ticketing.show.application.dto.ShowSeatResponse
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest
import com.culture.ticketing.show.exception.ShowAreaNotFoundException
import com.culture.ticketing.show.exception.DuplicatedShowSeatException
import com.culture.ticketing.show.infra.ShowSeatRepository
import com.culture.ticketing.show.domain.ShowSeat
import spock.lang.Specification

class ShowSeatServiceTest extends Specification {

    private ShowSeatRepository showSeatRepository = Mock();
    private ShowAreaService showAreaService = Mock();
    private ShowSeatService showSeatService = new ShowSeatService(showSeatRepository, showAreaService);

    def "공연 좌석 생성 성공"() {
        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(1L)
                .build();
        showAreaService.notExistsById(1L) >> false
        showSeatRepository.findByShowAreaIdAndShowSeatRowAndShowSeatNumber(request.getShowAreaId(), request.getShowSeatRow(), request.getShowSeatNumber()) >> Optional.empty()

        when:
        showSeatService.createShowSeat(request);

        then:
        1 * showSeatRepository.save(_) >> { args ->

            def savedSeat = args.get(0) as ShowSeat

            savedSeat.showSeatRow == "A"
            savedSeat.showSeatNumber == 1
            savedSeat.showAreaId == 1L
        }
    }

    def "공연 좌석 생성 시 요청 값에 null 이 존재하는 경우 예외 발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(null)
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 구역 아이디를 입력해주세요."
    }

    def "공연 좌석 생성 시 요청 값에 적절하지 않은 값이 들어간 경우 예외 발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(0)
                .showAreaId(1L)
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 번호를 1 이상 숫자로 입력해주세요."
    }

    def "공연 좌석 생성 시 이미 동일한 정보의 좌석이 존재하는 경우 예외 발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(1L)
                .build();
        showSeatRepository.findByShowAreaIdAndShowSeatRowAndShowSeatNumber(request.getShowAreaId(), request.getShowSeatRow(), request.getShowSeatNumber()) >> Optional.of(request.toEntity())

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(DuplicatedShowSeatException.class)
        e.message == "해당 장소에 동일한 좌석이 이미 존재합니다."
    }

    def "공연 좌석 생성 시 공연 구역 존재하지 않는 경우 예외 발생"() {

        given:
        Long showAreaId = 1L;
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatRow("A")
                .showSeatNumber(1)
                .showAreaId(showAreaId)
                .build();
        showAreaService.notExistsById(showAreaId) >> true

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(ShowAreaNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 구역입니다. (showAreaId = %d)", showAreaId)
    }

    def "공연 구역 아이디로 공연 좌석 목록 조회"() {

        given:
        showSeatRepository.findByShowAreaId(1L) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 3L)
        ]

        when:
        List<ShowSeatResponse> response = showSeatService.findShowSeatsByShowAreaId(1L);

        then:
        response.size() == 3
        response.showSeatId == [1L, 2L, 3L]
    }

    def "공연 좌석 아이디 목록으로 공연 좌석 목록 조회"() {

        given:
        List<Long> showSeatIds = [1L, 2L, 3L]
        showSeatRepository.findAllById(showSeatIds) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 3L)
        ]

        when:
        List<ShowSeatResponse> response = showSeatService.findByIds(showSeatIds);

        then:
        response.size() == 3
        response.showSeatId == [1L, 2L, 3L]
    }

    def "공연 좌석 아이디 목록으로 총 가격 조회"() {

        given:
        Set<Long> showSeatIds = [1L, 2L, 3L]

        showSeatRepository.findAllById(showSeatIds) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 2L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 3L, showAreaId: 3L)
        ]

        List<ShowAreaGradeResponse> showAreaGrades = [
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 1L, price: 100000)),
                new ShowAreaGradeResponse(ShowAreaGradeFixtures.createShowAreaGrade(showAreaGradeId: 2L, price: 50000)),
        ]

        showAreaService.findShowAreasByShowAreaIds([1L, 2L, 3L]) >> [
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 1L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 2L, showAreaGradeId: 1L), showAreaGrades.get(0)),
                new ShowAreaResponse(ShowAreaFixtures.createShowArea(showAreaId: 3L, showAreaGradeId: 2L), showAreaGrades.get(1)),
        ]

        when:
        int response = showSeatService.getTotalPriceByShowSeatIds(showSeatIds);

        then:
        response == 250000
    }

    def "공연 구역 아이디 목록으로 공연 좌석 목록 조회"() {

        given:
        List<Long> showAreaIds = [1L, 2L, 3L];
        showSeatRepository.findByShowAreaIdIn(showAreaIds) >> [
                ShowSeatFixtures.creatShowSeat(showSeatId: 1L, showAreaId: 1L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 2L, showAreaId: 2L),
                ShowSeatFixtures.creatShowSeat(showSeatId: 3L, showAreaId: 3L)
        ]

        when:
        List<ShowSeatResponse> response = showSeatService.findByShowAreaIds(showAreaIds);

        then:
        response.size() == 3
        response.showSeatId == [1L, 2L, 3L]
    }
}
