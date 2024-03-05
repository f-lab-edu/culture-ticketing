package com.culture.ticketing.show.exception;

public class DuplicatedShowSeatException extends RuntimeException {

    public DuplicatedShowSeatException() {
        super("해당 장소에 동일한 좌석이 이미 존재합니다.");
    }
}
