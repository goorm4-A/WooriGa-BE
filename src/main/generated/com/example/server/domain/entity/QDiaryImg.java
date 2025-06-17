package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiaryImg is a Querydsl query type for DiaryImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiaryImg extends EntityPathBase<DiaryImg> {

    private static final long serialVersionUID = -1516061573L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiaryImg diaryImg = new QDiaryImg("diaryImg");

    public final QFamilyDiary familyDiary;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public QDiaryImg(String variable) {
        this(DiaryImg.class, forVariable(variable), INITS);
    }

    public QDiaryImg(Path<? extends DiaryImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiaryImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiaryImg(PathMetadata metadata, PathInits inits) {
        this(DiaryImg.class, metadata, inits);
    }

    public QDiaryImg(Class<? extends DiaryImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyDiary = inits.isInitialized("familyDiary") ? new QFamilyDiary(forProperty("familyDiary"), inits.get("familyDiary")) : null;
    }

}

