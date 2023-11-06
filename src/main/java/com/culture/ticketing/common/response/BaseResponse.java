package com.culture.ticketing.common.response;

import com.culture.ticketing.common.exception.BaseException;
import lombok.Getter;

import static com.culture.ticketing.common.response.BaseResponseStatus.SUCCESS;

@Getter
public class BaseResponse<T> {

    private final int code;
    private final String message;
    private final T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(BaseResponseStatus status, T data) {
        this(status.getCode(), status.getMessage(), data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(SUCCESS, null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(SUCCESS, data);
    }

    public static <T> BaseResponse<T> error(BaseException e) {
        return new BaseResponse<>(e.getCode(), e.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(BaseException e, T data) {
        return new BaseResponse<>(e.getCode(), e.getMessage(), data);
    }
}
