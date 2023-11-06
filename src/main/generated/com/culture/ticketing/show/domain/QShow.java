package com.culture.ticketing.show.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShow is a Querydsl query type for Show
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShow extends EntityPathBase<Show> {

    private static final long serialVersionUID = -2022665733L;

    public static final QShow show = new QShow("show");

    public final com.culture.ticketing.common.entity.QBaseEntity _super = new com.culture.ticketing.common.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final EnumPath<AgeRestriction> ageRestriction = createEnum("ageRestriction", AgeRestriction.class);

    public final EnumPath<Category> category = createEnum("category", Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> latitude = createNumber("latitude", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> longitude = createNumber("longitude", java.math.BigDecimal.class);

    public final StringPath notice = createString("notice");

    public final StringPath placeName = createString("placeName");

    public final StringPath posterImgUrl = createString("posterImgUrl");

    public final NumberPath<Integer> runningTime = createNumber("runningTime", Integer.class);

    public final DatePath<java.time.LocalDate> showEndDate = createDate("showEndDate", java.time.LocalDate.class);

    public final NumberPath<Long> showId = createNumber("showId", Long.class);

    public final StringPath showName = createString("showName");

    public final DatePath<java.time.LocalDate> showStartDate = createDate("showStartDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShow(String variable) {
        super(Show.class, forVariable(variable));
    }

    public QShow(Path<? extends Show> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShow(PathMetadata metadata) {
        super(Show.class, metadata);
    }

}

