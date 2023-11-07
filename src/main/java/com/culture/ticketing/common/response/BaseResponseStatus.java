package com.culture.ticketing.common.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(200, "요청에 성공하였습니다."),

    BAD_REQUEST(400, "잘못된 요청값입니다."),

    DATABASE_ERROR(500, "데이터베이스 조회에 실패하였습니다."),
    UNKNOWN_ERROR(500, "알수 없는 에러가 발생하였습니다.");

    private final int code;
    private final String message;

}
