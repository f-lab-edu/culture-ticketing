package com.culture.ticketing.common.exception;

import com.culture.ticketing.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

import static com.culture.ticketing.common.response.BaseResponse.error;
import static com.culture.ticketing.common.response.BaseResponseStatus.DATABASE_ERROR;
import static com.culture.ticketing.common.response.BaseResponseStatus.UNKNOWN_ERROR;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public BaseResponse<?> handleBaseException(BaseException e) {
        log.error(e.getClass().getName(), e);
        return error(e);
    }

    @ExceptionHandler({SQLException.class})
    public BaseResponse<?> handleSQLException(Exception e) {
        log.error(e.getClass().getName(), e);
        return error(new BaseException(DATABASE_ERROR));
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public BaseResponse<?> handleException(Exception e) {
        log.error(e.getClass().getName(), e);
        return error(new BaseException(UNKNOWN_ERROR));
    }
}
