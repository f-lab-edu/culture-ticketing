package com.culture.ticketing.common.exception;

import com.culture.ticketing.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static com.culture.ticketing.common.response.BaseResponse.error;
import static com.culture.ticketing.common.response.BaseResponseStatus.*;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public BaseResponse<?> handleBaseException(BaseException e) {
        log.error(e.getClass().getName(), e);
        return error(e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getClass().getName(), e);
        return error(new BaseException(BAD_REQUEST), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
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
