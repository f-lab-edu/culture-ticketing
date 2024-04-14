package com.culture.ticketing.show.round_performer.exception;

public class PerformerNotFoundException extends RuntimeException {

    public PerformerNotFoundException(Long performerId) {
        super(String.format("존재하지 않는 출연자입니다. (performerId = %d)", performerId));
    }
}
