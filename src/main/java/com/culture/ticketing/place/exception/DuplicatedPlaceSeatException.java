package com.culture.ticketing.place.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class DuplicatedPlaceSeatException extends BaseException {

    public DuplicatedPlaceSeatException() {
        super(BaseResponseStatus.DUPLICATED_SEAT);
    }
}
