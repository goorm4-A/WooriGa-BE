package com.example.server.domain.entity;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CookingStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int stepIndex;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private FamilyRecipe recipe;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CookingImage> images = new ArrayList<>();

    public void setRecipe(FamilyRecipe recipe) {
        this.recipe = recipe;
    }

    public void addImage(CookingImage image) {
        images.add(image);
        image.setStep(this);
    }
}

