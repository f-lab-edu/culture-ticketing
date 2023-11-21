package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class ShowSeatGradeNotFoundException extends BaseException {

    public ShowSeatGradeNotFoundException() {
        super(BaseResponseStatus.NOT_FOUND_SHOW_SEAT_GRADE);
    }
}
