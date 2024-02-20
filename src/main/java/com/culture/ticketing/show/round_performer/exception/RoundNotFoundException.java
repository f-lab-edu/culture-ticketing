package com.culture.ticketing.show.round_performer.exception;

public class RoundNotFoundException extends RuntimeException {

    public RoundNotFoundException(Long roundId) {
        super(String.format("존재하지 않는 회차입니다. (roundId = %d)", roundId));
    }
}
