package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.global.ApiResponse;
import com.example.server.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cultures/{familyId}/recipes")
@RequiredArgsConstructor
public class FamilyRecipeRestController {

    private final RecipeService recipeService;

    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가족 요리법 생성")
    public ApiResponse<?> createRecipe(
            @PathVariable Long familyId,
            @RequestPart("recipe") RecipeRequestDTO.createRecipeDTO recipeDTO,
            @RequestPart("coverImages") List<MultipartFile> coverImages,
            @RequestPart Map<String, MultipartFile[]> stepImageParts,
            @AuthenticationPrincipal User user
    ) {
        Map<Integer, List<MultipartFile>> stepImages = extractStepImages(stepImageParts);
        recipeService.createRecipe(familyId,user, recipeDTO, coverImages, stepImages);
        return null;
    }

    private Map<Integer, List<MultipartFile>> extractStepImages(Map<String, MultipartFile[]> parts) {
        Map<Integer, List<MultipartFile>> result = new HashMap<>();

        for (Map.Entry<String, MultipartFile[]> entry : parts.entrySet()) {
            String key = entry.getKey(); // e.g., "stepImages_0"
            if (key.startsWith("stepImages_")) {
                int index = Integer.parseInt(key.replace("stepImages_", ""));
                result.put(index, Arrays.asList(entry.getValue()));
            }
        }
        return result;
    }

}
