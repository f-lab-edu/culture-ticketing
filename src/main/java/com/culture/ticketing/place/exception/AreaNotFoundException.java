package com.culture.ticketing.place.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class AreaNotFoundException extends BaseException {

    public AreaNotFoundException() {
        super(BaseResponseStatus.NOT_FOUND_AREA);
    }
}
