package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyDiary is a Querydsl query type for FamilyDiary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyDiary extends EntityPathBase<FamilyDiary> {

    private static final long serialVersionUID = -1467194012L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyDiary familyDiary = new QFamilyDiary("familyDiary");

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final EnumPath<com.example.server.domain.enums.FromType> contentType = createEnum("contentType", com.example.server.domain.enums.FromType.class);

    public final StringPath description = createString("description");

    public final ListPath<DiaryParticipant, QDiaryParticipant> diaryParticipants = this.<DiaryParticipant, QDiaryParticipant>createList("diaryParticipants", DiaryParticipant.class, QDiaryParticipant.class, PathInits.DIRECT2);

    public final ListPath<DiaryTag, QDiaryTag> diaryTags = this.<DiaryTag, QDiaryTag>createList("diaryTags", DiaryTag.class, QDiaryTag.class, PathInits.DIRECT2);

    public final QFamily family;

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<DiaryImg, QDiaryImg> images = this.<DiaryImg, QDiaryImg>createList("images", DiaryImg.class, QDiaryImg.class, PathInits.DIRECT2);

    public final StringPath location = createString("location");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> writtenDate = createDateTime("writtenDate", java.time.LocalDateTime.class);

    public QFamilyDiary(String variable) {
        this(FamilyDiary.class, forVariable(variable), INITS);
    }

    public QFamilyDiary(Path<? extends FamilyDiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyDiary(PathMetadata metadata, PathInits inits) {
        this(FamilyDiary.class, metadata, inits);
    }

    public QFamilyDiary(Class<? extends FamilyDiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new QFamily(forProperty("family")) : null;
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
    }

}

