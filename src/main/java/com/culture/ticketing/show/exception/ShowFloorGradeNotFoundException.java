package com.culture.ticketing.show.exception;

public class ShowFloorGradeNotFoundException extends RuntimeException {

    public ShowFloorGradeNotFoundException(Long showFloorGradeId) {
        super(String.format("존재하지 않는 공연 플로어 등급입니다. (showFloorGradeId = %d)", showFloorGradeId));
    }
}
