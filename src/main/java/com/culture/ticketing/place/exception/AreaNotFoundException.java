package com.culture.ticketing.place.exception;

import com.culture.ticketing.common.exception.BaseException;
import com.culture.ticketing.common.response.BaseResponseStatus;

public class AreaNotFoundException extends BaseException {

    public AreaNotFoundException(Long areaId) {
        super(BaseResponseStatus.NOT_FOUND_AREA, areaId);
    }

    public AreaNotFoundException(String areaIds) {
        super(BaseResponseStatus.NOT_FOUND_AREAS, areaIds);
    }
}
