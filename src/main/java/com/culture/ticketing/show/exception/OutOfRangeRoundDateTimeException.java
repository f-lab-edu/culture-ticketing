package com.culture.ticketing.show.exception;

public class OutOfRangeRoundDateTimeException extends RuntimeException {

    public OutOfRangeRoundDateTimeException() {
        super("공연 가능한 회차 날짜 범위를 벗어난 입력값입니다.");
    }
}
