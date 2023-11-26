package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class ShowNotFoundException extends BaseException {

    public ShowNotFoundException(Long showId) {
        super(BaseResponseStatus.NOT_FOUND_SHOW, showId);
    }
}
