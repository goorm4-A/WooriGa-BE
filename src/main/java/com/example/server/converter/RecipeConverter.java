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

    public static RecipeResponseDTO.RecipeDetailDTO toRecipeDetailDTO(FamilyRecipe recipe) {
        FamilyMember familyMember = recipe.getFamilyMember();
        String name = familyMember.getUser() != null
                ? familyMember.getUser().getName()
                : familyMember.getMemberName();

        List<String> covers = recipe.getCoverImages().stream()
                .filter(coverImage -> coverImage.getStep() == null)
                .map(CookingImage::getImageUrl)
                .collect(Collectors.toList());

        List<RecipeResponseDTO.RecipeStepDTO> steps = recipe.getSteps().stream()
                .map(step -> RecipeResponseDTO.RecipeStepDTO.builder()
                        .description(step.getDescription())
                        .imageUrl(step.getImages().isEmpty() ? null : step.getImages().get(0).getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return RecipeResponseDTO.RecipeDetailDTO.builder()
                .userName(name)
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .cookingTime(recipe.getCookingTime())
                .coverImages(covers)
                .ingredients(recipe.getIngredients())
                .steps(steps)
                .build();
    }
}
