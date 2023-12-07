package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class OutOfRangeRoundDateTime extends BaseException {

    public OutOfRangeRoundDateTime() {
        super(BaseResponseStatus.OUT_OF_RANGE_ROUND_DATE_TIME);
    }
}
