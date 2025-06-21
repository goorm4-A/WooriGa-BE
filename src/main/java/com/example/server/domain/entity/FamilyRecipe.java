package com.example.server.domain.entity;

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
public class FamilyRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description; // 설명

    private int cookingTime;

//    private String Ingredient; // 재료
    @ElementCollection
    @CollectionTable(name = "family_recipe_ingredient", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();

/*    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CookingStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CookingImage> coverImages = new ArrayList<>();

    public CookingStep addStep(int stepIndex, String description) {
        CookingStep step = new CookingStep();
        step.setStepIndex(stepIndex);
        step.setDescription(description);
        step.setRecipe(this);       // 양방향 설정
        this.steps.add(step);       // 리스트에 추가
        return step;
    }

    public void addCoverImage(CookingImage cookingImage) {
        coverImages.add(cookingImage);
    }
}

