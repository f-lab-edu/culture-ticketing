package com.culture.ticketing.show.application

import com.culture.ticketing.place.application.SeatService
import com.culture.ticketing.place.exception.SeatNotFoundException
import com.culture.ticketing.show.application.dto.ShowSeatSaveRequest
import com.culture.ticketing.show.exception.ShowSeatGradeNotFoundException
import com.culture.ticketing.show.infra.ShowSeatRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ShowSeatServiceTest extends Specification {

    @SpringBean
    private ShowSeatRepository showSeatRepository = Mock();
    @SpringBean
    private ShowSeatGradeService showSeatGradeService = Mock();
    @SpringBean
    private SeatService seatService = Mock();
    private ShowSeatService showSeatService = new ShowSeatService(showSeatRepository, showSeatGradeService, seatService);

    def "공연_좌석_정보_생성_시_공연_좌석_등급_아이디_값이_null_인_경우_예외_발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(null)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "공연 좌석 등급 아이디를 입력해주세요."
    }

    def "공연_좌석_정보_생성_시_좌석_아이디_목록_값이_null_인_경우_예외_발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(1L)
                .seatIds(null)
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(NullPointerException.class)
        e.message == "좌석 아이디를 입력해주세요."
    }

    def "공연_좌석_정보_생성_시_좌석_아이디_목록_사이즈가_0인_경우_예외_발생"() {

        given:
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(1L)
                .seatIds(Set.of())
                .build();

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "좌석 아이디를 입력해주세요."
    }

    def "공연_좌석_정보_생성_시_공연_좌석_등급_아이디_값에_해당하는_공연_좌석_등급이_존재하지_않는_경우_예외_발생"() {

        given:
        Long showSeatGradeId = 1L;
        ShowSeatSaveRequest request = ShowSeatSaveRequest.builder()
                .showSeatGradeId(showSeatGradeId)
                .seatIds(Set.of(1L, 2L, 3L, 4L, 5L))
                .build();
        showSeatGradeService.notExistsById(showSeatGradeId) >> true

        when:
        showSeatService.createShowSeat(request);

        then:
        def e = thrown(ShowSeatGradeNotFoundException.class)
        e.message == String.format("존재하지 않는 공연 좌석 등급입니다. (showSeatGradeId = %d)", showSeatGradeId)
    }
}
