package com.example.server.dto.familyRecipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RecipeResponseDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecipeInfoDTO {
        private Long id;
        private String userName;
        private String title;
        private int cookingTime;
        private String coverImage; // coverImage 0번 가져오기
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class RecipeListResponse {
        private List<RecipeInfoDTO> recipes;
        private boolean hasNext;
        private String nextCursor;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecipeStepDTO {
        private String description;
        private String imageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecipeDetailDTO {
        private String userName;
        private String title;
        private String description;
        private int cookingTime;
        private List<String> coverImages;
        private List<String> ingredients;
        private List<RecipeStepDTO> steps;
    }
}
