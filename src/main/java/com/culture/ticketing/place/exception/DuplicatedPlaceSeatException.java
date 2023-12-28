package com.culture.ticketing.place.exception;

public class DuplicatedPlaceSeatException extends RuntimeException {

    public DuplicatedPlaceSeatException() {
        super("해당 장소에 동일한 좌석이 이미 존재합니다.");
    }
}
