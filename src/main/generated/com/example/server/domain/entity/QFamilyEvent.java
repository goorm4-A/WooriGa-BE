package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyEvent is a Querydsl query type for FamilyEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyEvent extends EntityPathBase<FamilyEvent> {

    private static final long serialVersionUID = -1465879493L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyEvent familyEvent = new QFamilyEvent("familyEvent");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath location = createString("location");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final DatePath<java.time.LocalDate> timeline = createDate("timeline", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QFamilyEvent(String variable) {
        this(FamilyEvent.class, forVariable(variable), INITS);
    }

    public QFamilyEvent(Path<? extends FamilyEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyEvent(PathMetadata metadata, PathInits inits) {
        this(FamilyEvent.class, metadata, inits);
    }

    public QFamilyEvent(Class<? extends FamilyEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

