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

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final NumberPath<Integer> cookingTime = createNumber("cookingTime", Integer.class);

    public final StringPath coverImage = createString("coverImage");

    public final ListPath<CookingImage, QCookingImage> coverImages = this.<CookingImage, QCookingImage>createList("coverImages", CookingImage.class, QCookingImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final QFamilyMember familyMember;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> ingredients = this.<String, StringPath>createList("ingredients", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<CookingStep, QCookingStep> steps = this.<CookingStep, QCookingStep>createList("steps", CookingStep.class, QCookingStep.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

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
    }

}

