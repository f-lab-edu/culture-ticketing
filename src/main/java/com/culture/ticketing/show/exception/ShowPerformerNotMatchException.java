package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class ShowPerformerNotMatchException extends BaseException {

    public ShowPerformerNotMatchException(String performerIds) {
        super(BaseResponseStatus.SHOW_PERFORMER_NOT_MATCH, performerIds);
    }
}
