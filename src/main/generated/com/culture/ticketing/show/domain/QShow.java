package com.culture.ticketing.show.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShow is a Querydsl query type for Show
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShow extends EntityPathBase<Show> {

    private static final long serialVersionUID = -2022665733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShow show = new QShow("show");

    public final com.culture.ticketing.common.entity.QBaseEntity _super = new com.culture.ticketing.common.entity.QBaseEntity(this);

    public final EnumPath<AgeRestriction> ageRestriction = createEnum("ageRestriction", AgeRestriction.class);

    public final EnumPath<Category> category = createEnum("category", Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final StringPath notice = createString("notice");

    public final QPlace place;

    public final StringPath posterImgUrl = createString("posterImgUrl");

    public final NumberPath<Integer> runningTime = createNumber("runningTime", Integer.class);

    public final DatePath<java.time.LocalDate> showEndDate = createDate("showEndDate", java.time.LocalDate.class);

    public final NumberPath<Long> showId = createNumber("showId", Long.class);

    public final StringPath showName = createString("showName");

    public final DatePath<java.time.LocalDate> showStartDate = createDate("showStartDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShow(String variable) {
        this(Show.class, forVariable(variable), INITS);
    }

    public QShow(Path<? extends Show> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShow(PathMetadata metadata, PathInits inits) {
        this(Show.class, metadata, inits);
    }

    public QShow(Class<? extends Show> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place")) : null;
    }

}

