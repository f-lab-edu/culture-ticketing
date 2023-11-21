package com.culture.ticketing.common.response;


import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    SUCCESS(200, "요청에 성공하였습니다."),

    EMPTY_PLACE_ADDRESS(400, "장소 주소를 입력해주세요."),
    EMPTY_PLACE_LATITUDE(400, "정확한 장소 위도를 입력해주세요."),
    EMPTY_PLACE_LONGITUDE(400, "정확한 장소 경도를 입력해주세요."),
    EMPTY_SHOW_CATEGORY(400, "공연 카테고리를 입력해주세요."),
    EMPTY_SHOW_AGE_RESTRICTION(400, "공연 관람제한을 입력해주세요."),
    EMPTY_SHOW_NAME(400, "공연 입력을 입력해주세요."),
    EMPTY_SHOW_START_DATE(400, "공연 시작 날짜를 입력해주세요."),
    EMPTY_SHOW_END_DATE(400, "공연 종료 날짜를 입력해주세요."),
    EMPTY_SHOW_POSTER_IMG_URL(400, "공연 포스터 이미지 url을 입력해주세요."),
    NOT_POSITIVE_SHOW_RUNNING_TIME(400, "공연 러닝 시간을 정확히 입력해주세요."),
    EMPTY_SHOW_PLACE_ID(400, "공연 장소 아이디를 입력해주세요."),
    EMPTY_SHOW_ID(400, "공연 아이디를 입력해주세요."),
    EMPTY_SHOW_SCHEDULE_DATE(400, "공연 일정 날짜(yyyy-MM-dd)를 입력해주세요."),
    EMPTY_SHOW_SCHEDULE_TIME(400, "공연 일정 시간(hh:mm)을 입력해주세요."),
    DUPLICATED_SHOW_SCHEDULE(400, "해당 공연에 동일한 일정이 이미 존재합니다."),
    EMPTY_PLACE_ID(400, "장소 아이디를 입력해주세요."),
    EMPTY_SEAT_AREA(400, "좌석 구역을 입력해주세요."),
    NEGATIVE_SEAT_ROW(400, "좌석 행을 1 이상 숫자로 입력해주세요."),
    NEGATIVE_SEAT_NUMBER(400, "좌석 번호를 1 이상 숫자로 입력해주세요."),
    DUPLICATED_SEAT(400, "해당 장소에 동일한 좌석이 이미 존재합니다."),
    EMPTY_SHOW_SEAT_GRADE(400, "공연 좌석 등급을 입력해주세요."),
    NEGATIVE_SHOW_SEAT_PRICE(400, "공연 좌석 가격을 0 이상으로 입력해주세요."),
    EMPTY_SHOW_SEAT_GRADE_ID(400, "공연 좌석 등급 아이디를 입력해주세요."),
    EMPTY_SEAT_ID(400, "좌석 아이디를 입력해주세요."),

    NOT_FOUND_PLACE(404, "존재하지 않는 장소입니다."),
    NOT_FOUND_SHOW(404, "존재하지 않는 공연입니다."),
    NOT_FOUND_SHOW_SEAT_GRADE(404, "존재하지 않는 공연 좌석 등급입니다."),
    NOT_FOUND_SEAT(404, "존재하지 않는 좌석입니다."),

    DATABASE_ERROR(500, "데이터베이스 조회에 실패하였습니다."),
    UNKNOWN_ERROR(500, "알수 없는 에러가 발생하였습니다.");

    private final int code;
    private final String message;

    BaseResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
