package com.culture.ticketing.place.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class SeatNotFoundException extends BaseException {

    public SeatNotFoundException(Long seatId) {
        super(BaseResponseStatus.NOT_FOUND_SEAT, seatId);
    }
}
