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
public class FamilyRecipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description; // 설명

    private int cookingTime;

    private String coverImage; // 대표 레시피 이미지

    @ElementCollection
    @CollectionTable(name = "family_recipe_ingredient", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    @Builder.Default
    private List<String> ingredients = new ArrayList<>();

/*    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CookingStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CookingImage> coverImages = new ArrayList<>();


    @OneToMany(mappedBy="recipe",fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
    @OrderBy("id asc") //댓글 정렬
    private List<Comment> comments;

    public CookingStep addStep(int stepIndex, String description) {
        CookingStep step = new CookingStep();
        step.setStepIndex(stepIndex);
        step.setDescription(description);
        step.setRecipe(this);       // 양방향 설정
        this.steps.add(step);       // 리스트에 추가
        return step;
    }

    public void addCoverImage(CookingImage cookingImage) {
        cookingImage.setRecipe(this);
        coverImages.add(cookingImage);
    }
}

