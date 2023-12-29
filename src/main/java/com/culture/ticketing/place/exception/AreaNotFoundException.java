package com.culture.ticketing.place.exception;

public class AreaNotFoundException extends RuntimeException {

    public AreaNotFoundException(Long areaId) {
        super(String.format("존재하지 않는 구역입니다. (areaId = %d)", areaId));
    }
}
