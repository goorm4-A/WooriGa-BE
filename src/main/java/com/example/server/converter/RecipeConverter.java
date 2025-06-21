package com.example.server.converter;

import com.example.server.domain.entity.CookingImage;
import com.example.server.domain.entity.CookingStep;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.FamilyRecipe;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.dto.familyRecipe.RecipeResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeConverter {
    public static FamilyRecipe toFamilyRecipe(RecipeRequestDTO.createRecipeDTO request, FamilyMember familyMember) {
        return new FamilyRecipe().builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .cookingTime(request.getCookingTime())
                .ingredients(request.getIngredients())
                .familyMember(familyMember)
                .build();
    }

    public static List<CookingStep> toCookingSteps(List<RecipeRequestDTO.cookingStepDTO> stepDTOList) {
        return stepDTOList.stream()
                .map(dto -> CookingStep.builder()
                        .description(dto.getDescription())
                        .stepIndex(dto.getImageIndexes())
                        .build())
                .collect(Collectors.toList());

    }

    public static CookingImage toCookingImage(String url, CookingStep step, FamilyRecipe recipe) {
        return new CookingImage().builder()
                .imageUrl(url)
                .step(step)
                .recipe(recipe)
                .build();
    }

    public static RecipeResponseDTO.RecipeInfoDTO toRecipeInfoDTO(FamilyRecipe recipe) {
        FamilyMember familyMember = recipe.getFamilyMember();
        String name = familyMember.getUser() != null
                ? familyMember.getUser().getName()
                : familyMember.getMemberName();
        String coverImage = recipe.getCoverImages().isEmpty() ? null :
                recipe.getCoverImages().get(0).getImageUrl();
        return RecipeResponseDTO.RecipeInfoDTO.builder()
                .id(recipe.getId())
                .userName(name)
                .title(recipe.getTitle())
                .cookingTime(recipe.getCookingTime())
                .coverImage(coverImage)
                .build();
    }
}
