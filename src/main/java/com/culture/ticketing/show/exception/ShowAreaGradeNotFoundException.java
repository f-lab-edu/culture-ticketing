package com.culture.ticketing.show.exception;

public class ShowAreaGradeNotFoundException extends RuntimeException {

    public ShowAreaGradeNotFoundException(Long showAreaGradeId) {
        super(String.format("존재하지 않는 공연 구역 등급입니다. (showAreaGradeId = %d)", showAreaGradeId));
    }
}
