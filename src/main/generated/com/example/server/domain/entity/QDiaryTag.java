package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryTag is a Querydsl query type for DiaryTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiaryTag extends EntityPathBase<DiaryTag> {

    private static final long serialVersionUID = -1516051374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryTag diaryTag = new QDiaryTag("diaryTag");

    public final QFamilyDiary familyDiary;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTag tag;

    public QDiaryTag(String variable) {
        this(DiaryTag.class, forVariable(variable), INITS);
    }

    public QDiaryTag(Path<? extends DiaryTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryTag(PathMetadata metadata, PathInits inits) {
        this(DiaryTag.class, metadata, inits);
    }

    public QDiaryTag(Class<? extends DiaryTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyDiary = inits.isInitialized("familyDiary") ? new QFamilyDiary(forProperty("familyDiary"), inits.get("familyDiary")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

