package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnniversaryTag is a Querydsl query type for AnniversaryTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnniversaryTag extends EntityPathBase<AnniversaryTag> {

    private static final long serialVersionUID = -1777704667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnniversaryTag anniversaryTag = new QAnniversaryTag("anniversaryTag");

    public final QFamilyAnniversary familyAnniversary;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTag tag;

    public QAnniversaryTag(String variable) {
        this(AnniversaryTag.class, forVariable(variable), INITS);
    }

    public QAnniversaryTag(Path<? extends AnniversaryTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnniversaryTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnniversaryTag(PathMetadata metadata, PathInits inits) {
        this(AnniversaryTag.class, metadata, inits);
    }

    public QAnniversaryTag(Class<? extends AnniversaryTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyAnniversary = inits.isInitialized("familyAnniversary") ? new QFamilyAnniversary(forProperty("familyAnniversary"), inits.get("familyAnniversary")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

