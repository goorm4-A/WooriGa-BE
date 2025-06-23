package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyMember is a Querydsl query type for FamilyMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyMember extends EntityPathBase<FamilyMember> {

    private static final long serialVersionUID = 2015935769L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyMember familyMember = new QFamilyMember("familyMember");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFamily family;

    public final ListPath<FamilyAnniversary, QFamilyAnniversary> familyAnniversaries = this.<FamilyAnniversary, QFamilyAnniversary>createList("familyAnniversaries", FamilyAnniversary.class, QFamilyAnniversary.class, PathInits.DIRECT2);

    public final ListPath<FamilyDiary, QFamilyDiary> familyDiaries = this.<FamilyDiary, QFamilyDiary>createList("familyDiaries", FamilyDiary.class, QFamilyDiary.class, PathInits.DIRECT2);

    public final ListPath<FamilyEvent, QFamilyEvent> familyEvents = this.<FamilyEvent, QFamilyEvent>createList("familyEvents", FamilyEvent.class, QFamilyEvent.class, PathInits.DIRECT2);

    public final ListPath<FamilyMotto, QFamilyMotto> familyMottos = this.<FamilyMotto, QFamilyMotto>createList("familyMottos", FamilyMotto.class, QFamilyMotto.class, PathInits.DIRECT2);

    public final ListPath<FamilyRecipe, QFamilyRecipe> familyRecipes = this.<FamilyRecipe, QFamilyRecipe>createList("familyRecipes", FamilyRecipe.class, QFamilyRecipe.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final BooleanPath isUserAdded = createBoolean("isUserAdded");

    public final DatePath<java.time.LocalDate> memberBirthDate = createDate("memberBirthDate", java.time.LocalDate.class);

    public final StringPath memberName = createString("memberName");

    public final StringPath relation = createString("relation");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QFamilyMember(String variable) {
        this(FamilyMember.class, forVariable(variable), INITS);
    }

    public QFamilyMember(Path<? extends FamilyMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyMember(PathMetadata metadata, PathInits inits) {
        this(FamilyMember.class, metadata, inits);
    }

    public QFamilyMember(Class<? extends FamilyMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.family = inits.isInitialized("family") ? new QFamily(forProperty("family")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

