package com.example.server.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCookingStep is a Querydsl query type for CookingStep
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCookingStep extends EntityPathBase<CookingStep> {

    private static final long serialVersionUID = -1902563285L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCookingStep cookingStep = new QCookingStep("cookingStep");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<CookingImage, QCookingImage> images = this.<CookingImage, QCookingImage>createList("images", CookingImage.class, QCookingImage.class, PathInits.DIRECT2);

    public final QFamilyRecipe recipe;

    public final NumberPath<Integer> stepIndex = createNumber("stepIndex", Integer.class);

    public QCookingStep(String variable) {
        this(CookingStep.class, forVariable(variable), INITS);
    }

    public QCookingStep(Path<? extends CookingStep> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCookingStep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCookingStep(PathMetadata metadata, PathInits inits) {
        this(CookingStep.class, metadata, inits);
    }

    public QCookingStep(Class<? extends CookingStep> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QFamilyRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

