package com.example.server.dto.familyRecipe;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeCommentResponse {
    private final List<RecipeResponseDTO.recipeCommentDto> comments;
    private final boolean hasNext;
}
