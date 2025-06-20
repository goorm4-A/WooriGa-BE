package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyAnniversary is a Querydsl query type for FamilyAnniversary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyAnniversary extends EntityPathBase<FamilyAnniversary> {

    private static final long serialVersionUID = -1974537103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyAnniversary familyAnniversary = new QFamilyAnniversary("familyAnniversary");

    public final EnumPath<com.example.server.domain.enums.AnniversaryType> anniversaryType = createEnum("anniversaryType", com.example.server.domain.enums.AnniversaryType.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final QFamily family;

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath title = createString("title");

    public final QUser user;

    public QFamilyAnniversary(String variable) {
        this(FamilyAnniversary.class, forVariable(variable), INITS);
    }

    public QFamilyAnniversary(Path<? extends FamilyAnniversary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyAnniversary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyAnniversary(PathMetadata metadata, PathInits inits) {
        this(FamilyAnniversary.class, metadata, inits);
    }

    public QFamilyAnniversary(Class<? extends FamilyAnniversary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new QFamily(forProperty("family")) : null;
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

