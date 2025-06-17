package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamilyRecipe is a Querydsl query type for FamilyRecipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFamilyRecipe extends EntityPathBase<FamilyRecipe> {

    private static final long serialVersionUID = -2136176627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamilyRecipe familyRecipe = new QFamilyRecipe("familyRecipe");

    public final StringPath description = createString("description");

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath Ingredient = createString("Ingredient");

    public final StringPath title = createString("title");

    public final QUser user;

    public QFamilyRecipe(String variable) {
        this(FamilyRecipe.class, forVariable(variable), INITS);
    }

    public QFamilyRecipe(Path<? extends FamilyRecipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamilyRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamilyRecipe(PathMetadata metadata, PathInits inits) {
        this(FamilyRecipe.class, metadata, inits);
    }

    public QFamilyRecipe(Class<? extends FamilyRecipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.familyMember = inits.isInitialized("familyMember") ? new QFamilyMember(forProperty("familyMember"), inits.get("familyMember")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

