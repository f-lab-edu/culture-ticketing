package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class DuplicatedRoundDateTimeException extends BaseException {

    public DuplicatedRoundDateTimeException() {
        super(BaseResponseStatus.DUPLICATED_ROUND_DATE_TIME);
    }
}
