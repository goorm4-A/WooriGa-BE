package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyMotto is a Querydsl query type for FamilyMotto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyMotto extends EntityPathBase<FamilyMotto> {

    private static final long serialVersionUID = -1458685266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyMotto familyMotto = new QFamilyMotto("familyMotto");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final QFamily family;

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.example.server.domain.enums.RuleType> ruleType = createEnum("ruleType", com.example.server.domain.enums.RuleType.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QFamilyMotto(String variable) {
        this(FamilyMotto.class, forVariable(variable), INITS);
    }

    public QFamilyMotto(Path<? extends FamilyMotto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyMotto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyMotto(PathMetadata metadata, PathInits inits) {
        this(FamilyMotto.class, metadata, inits);
    }

    public QFamilyMotto(Class<? extends FamilyMotto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new QFamily(forProperty("family")) : null;
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
    }

}

