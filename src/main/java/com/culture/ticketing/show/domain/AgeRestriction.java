package com.culture.ticketing.show.domain;

public enum AgeRestriction {
    ALL("전체관람가"),
    TWELVE("12세 이상 관람가"),
    FIFTEEN("15세 이상 관람가"),
    ADULT("청소년 관람불가");

    private final String ageRestrictionName;

    AgeRestriction(String ageRestrictionName) {
        this.ageRestrictionName = ageRestrictionName;
    }

    public String getAgeRestrictionName() {
        return this.ageRestrictionName;
    }
}
