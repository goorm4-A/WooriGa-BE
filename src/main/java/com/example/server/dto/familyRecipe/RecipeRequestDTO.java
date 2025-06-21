package com.example.server.dto.familyRecipe;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeRequestDTO {

    @Getter
    public static class createRecipeDTO {
        private String title;
        private String description;
        private int cookingTime;
        private List<String> ingredients;
        private List<cookingStepDTO> steps;
    }

    @Getter
    public static class cookingStepDTO {
        private String description;
        private Integer imageIndexes;
    }
    @Getter
    public static class addRecipeCommentRequest {
        private String content;
        private Long familyMemberId; //댓글 작성자
        private Long familyRecipeId; //댓글의 원 게시글
    }
}
