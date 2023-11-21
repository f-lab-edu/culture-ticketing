package com.culture.ticketing.show.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class DuplicatedShowScheduleException extends BaseException {

    public DuplicatedShowScheduleException() {
        super(BaseResponseStatus.DUPLICATED_SHOW_SCHEDULE);
    }
}
