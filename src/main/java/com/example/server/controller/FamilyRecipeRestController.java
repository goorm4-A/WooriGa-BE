package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
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
            @RequestPart(value = "coverImages", required = false) List<MultipartFile> coverImages,
            @RequestPart(value = "stepImages", required = false) List<MultipartFile> stepImages,
            @AuthenticationPrincipal User user
    ) {
        recipeService.createRecipe(familyId,user, recipeDTO, coverImages, stepImages);
        return ApiResponse.onSuccess(SuccessStatus.CREATE_MOTTO_SUCCESSFUL);
    }


}
