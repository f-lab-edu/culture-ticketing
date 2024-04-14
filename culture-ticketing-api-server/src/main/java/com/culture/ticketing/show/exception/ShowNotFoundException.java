package com.culture.ticketing.show.exception;

public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException(Long showId) {
        super(String.format("존재하지 않는 공연입니다. (showId = %d)", showId));
    }
}
