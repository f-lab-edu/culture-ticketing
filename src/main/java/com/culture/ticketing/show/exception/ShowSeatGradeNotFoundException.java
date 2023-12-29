package com.culture.ticketing.show.exception;

public class ShowSeatGradeNotFoundException extends RuntimeException {

    public ShowSeatGradeNotFoundException(Long showSeatGradeId) {
        super(String.format("존재하지 않는 공연 좌석 등급입니다. (showSeatGradeId = %d)", showSeatGradeId));
    }
}
