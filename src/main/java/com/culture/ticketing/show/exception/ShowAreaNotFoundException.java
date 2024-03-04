package com.culture.ticketing.show.exception;

public class ShowAreaNotFoundException extends RuntimeException {

    public ShowAreaNotFoundException(Long showAreaId) {
        super(String.format("존재하지 않는 공연 구역입니다. (showAreaId = %d)", showAreaId));
    }
}
