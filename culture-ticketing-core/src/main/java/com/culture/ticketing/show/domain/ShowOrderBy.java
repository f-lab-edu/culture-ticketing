package com.culture.ticketing.show.domain;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

import static com.culture.ticketing.show.domain.QShow.show;

public enum ShowOrderBy {
    NEWEST,
    SHOW_NAME_ASC;

    public OrderSpecifier getOrderSpecifier() {

        switch (this) {
            case NEWEST:
                return new OrderSpecifier<>(Order.DESC, show.createdAt);
            case SHOW_NAME_ASC:
                return new OrderSpecifier<>(Order.ASC, show.showName);
            default:
                return new OrderSpecifier<>(Order.ASC, show.showId);
        }
    }
}
