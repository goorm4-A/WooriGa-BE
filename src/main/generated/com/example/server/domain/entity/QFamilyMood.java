package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyMood is a Querydsl query type for FamilyMood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyMood extends EntityPathBase<FamilyMood> {

    private static final long serialVersionUID = -47054538L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyMood familyMood = new QFamilyMood("familyMood");

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MoodTag, QMoodTag> moodTags = this.<MoodTag, QMoodTag>createList("moodTags", MoodTag.class, QMoodTag.class, PathInits.DIRECT2);

    public final EnumPath<com.example.server.domain.enums.MoodType> moodType = createEnum("moodType", com.example.server.domain.enums.MoodType.class);

    public final QUser user;

    public QFamilyMood(String variable) {
        this(FamilyMood.class, forVariable(variable), INITS);
    }

    public QFamilyMood(Path<? extends FamilyMood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyMood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyMood(PathMetadata metadata, PathInits inits) {
        this(FamilyMood.class, metadata, inits);
    }

    public QFamilyMood(Class<? extends FamilyMood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

