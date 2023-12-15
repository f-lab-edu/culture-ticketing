package com.culture.ticketing.common.utils;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.function.Function;

public class QueryUtils {

    public static <T> BooleanExpression ifNotNull(Function<T, BooleanExpression> function, T object) {
        return object != null ? function.apply(object) : null;
    }
}
