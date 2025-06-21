package com.example.server.dto.familyRecipe;

import lombok.Getter;

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
}
