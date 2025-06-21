package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CookingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "family_recipe_id")
    private FamilyRecipe recipe;

    @ManyToOne
    @JoinColumn(name = "cooking_step_id")
    private CookingStep step;

    public void setStep(CookingStep cookingStep) {
        this.step = cookingStep;
    }
}
