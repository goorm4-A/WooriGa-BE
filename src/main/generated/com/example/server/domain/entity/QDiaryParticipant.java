package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryParticipant is a Querydsl query type for DiaryParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiaryParticipant extends EntityPathBase<DiaryParticipant> {

    private static final long serialVersionUID = -752165909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryParticipant diaryParticipant = new QDiaryParticipant("diaryParticipant");

    public final QFamilyDiary familyDiary;

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QDiaryParticipant(String variable) {
        this(DiaryParticipant.class, forVariable(variable), INITS);
    }

    public QDiaryParticipant(Path<? extends DiaryParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryParticipant(PathMetadata metadata, PathInits inits) {
        this(DiaryParticipant.class, metadata, inits);
    }

    public QDiaryParticipant(Class<? extends DiaryParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyDiary = inits.isInitialized("familyDiary") ? new QFamilyDiary(forProperty("familyDiary"), inits.get("familyDiary")) : null;
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

