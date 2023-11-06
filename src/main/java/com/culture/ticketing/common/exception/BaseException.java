package com.culture.ticketing.common.exception;

import com.culture.ticketing.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final int code;
    private final String message;

    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

}
