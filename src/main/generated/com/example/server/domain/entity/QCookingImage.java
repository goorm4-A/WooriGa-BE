package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCookingImage is a Querydsl query type for CookingImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCookingImage extends EntityPathBase<CookingImage> {

    private static final long serialVersionUID = 1140632540L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCookingImage cookingImage = new QCookingImage("cookingImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QFamilyRecipe recipe;

    public final QCookingStep step;

    public QCookingImage(String variable) {
        this(CookingImage.class, forVariable(variable), INITS);
    }

    public QCookingImage(Path<? extends CookingImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCookingImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCookingImage(PathMetadata metadata, PathInits inits) {
        this(CookingImage.class, metadata, inits);
    }

    public QCookingImage(Class<? extends CookingImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QFamilyRecipe(forProperty("recipe"), inits.get("recipe")) : null;
        this.step = inits.isInitialized("step") ? new QCookingStep(forProperty("step"), inits.get("step")) : null;
    }

}

