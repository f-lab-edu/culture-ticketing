package com.culture.ticketing.show.round_performer.exception;

public class ShowPerformerNotMatchException extends RuntimeException {

    public ShowPerformerNotMatchException(String performerIds) {
        super(String.format("해당 공연의 출연자가 아닌 값이 포함되어 있습니다. (performerIds = %s)", performerIds));
    }
}
