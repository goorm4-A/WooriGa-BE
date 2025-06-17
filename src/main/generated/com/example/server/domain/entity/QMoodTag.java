package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMoodTag is a Querydsl query type for MoodTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoodTag extends EntityPathBase<MoodTag> {

    private static final long serialVersionUID = -1159791032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMoodTag moodTag = new QMoodTag("moodTag");

    public final QFamilyMood familyMood;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTag tag;

    public QMoodTag(String variable) {
        this(MoodTag.class, forVariable(variable), INITS);
    }

    public QMoodTag(Path<? extends MoodTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMoodTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMoodTag(PathMetadata metadata, PathInits inits) {
        this(MoodTag.class, metadata, inits);
    }

    public QMoodTag(Class<? extends MoodTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyMood = inits.isInitialized("familyMood") ? new QFamilyMood(forProperty("familyMood"), inits.get("familyMood")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

