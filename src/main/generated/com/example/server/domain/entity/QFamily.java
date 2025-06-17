package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamily is a Querydsl query type for Family
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamily extends EntityPathBase<Family> {

    private static final long serialVersionUID = 26294687L;

    public static final QFamily family = new QFamily("family");

    public final ListPath<FamilyDiary, QFamilyDiary> familyDiaries = this.<FamilyDiary, QFamilyDiary>createList("familyDiaries", FamilyDiary.class, QFamilyDiary.class, PathInits.DIRECT2);

    public final ListPath<FamilyMember, QFamilyMember> familyMembers = this.<FamilyMember, QFamilyMember>createList("familyMembers", FamilyMember.class, QFamilyMember.class, PathInits.DIRECT2);

    public final ListPath<FamilyMotto, QFamilyMotto> familyMottos = this.<FamilyMotto, QFamilyMotto>createList("familyMottos", FamilyMotto.class, QFamilyMotto.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> inviteCode = createNumber("inviteCode", Integer.class);

    public final StringPath mood = createString("mood");

    public final StringPath motto = createString("motto");

    public final StringPath name = createString("name");

    public QFamily(String variable) {
        super(Family.class, forVariable(variable));
    }

    public QFamily(Path<? extends Family> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFamily(PathMetadata metadata) {
        super(Family.class, metadata);
    }

}

