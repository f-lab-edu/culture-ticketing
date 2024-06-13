package com.culture.ticketing.show.domain;

import com.querydsl.core.BooleanBuilder;

import static com.culture.ticketing.show.domain.QShow.show;

public class ShowFilter {

    private final Category category;
    private final String showName;

    public ShowFilter(Category category, String showName) {
        this.category = category;
        this.showName = showName;
    }

    public BooleanBuilder getShowFilter() {

        BooleanBuilder builder = new BooleanBuilder();

        if (this.category != null) {
            builder.and(show.category.eq(this.category));
        }
        if (this.showName != null && !this.showName.isEmpty()) {
            builder.and(show.showName.contains(showName));
        }

        return builder;
    }
}
