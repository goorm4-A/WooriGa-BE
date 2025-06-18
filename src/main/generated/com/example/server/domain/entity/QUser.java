package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1447551802L;

    public static final QUser user = new QUser("user");

    public final StringPath accessToken = createString("accessToken");

    public final ListPath<Alarm, QAlarm> alarms = this.<Alarm, QAlarm>createList("alarms", Alarm.class, QAlarm.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> birthDateTime = createDateTime("birthDateTime", java.time.LocalDateTime.class);

    public final StringPath birthPlace = createString("birthPlace");

    public final ListPath<DiaryParticipant, QDiaryParticipant> diaryParticipants = this.<DiaryParticipant, QDiaryParticipant>createList("diaryParticipants", DiaryParticipant.class, QDiaryParticipant.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<FamilyAnniversary, QFamilyAnniversary> familyAnniversaries = this.<FamilyAnniversary, QFamilyAnniversary>createList("familyAnniversaries", FamilyAnniversary.class, QFamilyAnniversary.class, PathInits.DIRECT2);

    public final ListPath<FamilyEvent, QFamilyEvent> familyEvents = this.<FamilyEvent, QFamilyEvent>createList("familyEvents", FamilyEvent.class, QFamilyEvent.class, PathInits.DIRECT2);

    public final ListPath<FamilyMember, QFamilyMember> familyMembers = this.<FamilyMember, QFamilyMember>createList("familyMembers", FamilyMember.class, QFamilyMember.class, PathInits.DIRECT2);

    public final ListPath<FamilyRecipe, QFamilyRecipe> familyRecipes = this.<FamilyRecipe, QFamilyRecipe>createList("familyRecipes", FamilyRecipe.class, QFamilyRecipe.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath refreshToken = createString("refreshToken");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

