package com.culture.ticketing.show.domain;

public enum Category {

    MUSICAL("뮤지컬"),
    PLAY("연극"),
    CONCERT("콘서트"),
    SPORTS("스포츠"),
    EXHIBITION("전시"),
    CLASSIC("클래식");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
