package com.culture.ticketing.place.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class PlaceNotFoundException extends BaseException {

    public PlaceNotFoundException() {
        super(BaseResponseStatus.NOT_FOUND_PLACE);
    }
}
