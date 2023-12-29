package com.culture.ticketing.show.exception;

public class DuplicatedRoundDateTimeException extends RuntimeException {

    public DuplicatedRoundDateTimeException() {
        super("해당 공연에 일정이 동일한 회차가 이미 존재합니다.");
    }
}
