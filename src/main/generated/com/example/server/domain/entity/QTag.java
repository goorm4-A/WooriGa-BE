package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1016528065L;

    public static final QTag tag = new QTag("tag");

    public final ListPath<AnniversaryTag, QAnniversaryTag> anniversaryTags = this.<AnniversaryTag, QAnniversaryTag>createList("anniversaryTags", AnniversaryTag.class, QAnniversaryTag.class, PathInits.DIRECT2);

    public final ListPath<DiaryTag, QDiaryTag> diaryTags = this.<DiaryTag, QDiaryTag>createList("diaryTags", DiaryTag.class, QDiaryTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MoodTag, QMoodTag> moodTags = this.<MoodTag, QMoodTag>createList("moodTags", MoodTag.class, QMoodTag.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

