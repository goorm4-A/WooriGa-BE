package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.AddCommentRequest;
import com.example.server.dto.familyDiary.CommentDto;
import com.example.server.dto.familyRecipe.RecipeCommentResponse;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.dto.familyRecipe.RecipeResponseDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.SwaggerBody;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.CommentService;
import com.example.server.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cultures/{familyId}/recipes")
@RequiredArgsConstructor
public class FamilyRecipeRestController {

    private final RecipeService recipeService;
    private final CommentService commentService;
    @SwaggerBody(content = @Content(
            encoding = @Encoding(name = "recipe", contentType = MediaType.APPLICATION_JSON_VALUE)
    ))
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

    @GetMapping("")
    @Operation(summary = "가족 요리법 목록 조회")
    public ApiResponse<RecipeResponseDTO.RecipeListResponse> getRecipeList(
            @PathVariable Long familyId,
            @Parameter(description = "조회한 마지막 recipeId") @RequestParam(required = false) Long lastRecipeId,
            @PageableDefault(size = 8) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        RecipeResponseDTO.RecipeListResponse result = recipeService.getRecipeList(familyId, user, lastRecipeId, pageable);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "가족 요리법 상세 조회")
    public ApiResponse<RecipeResponseDTO.RecipeDetailDTO> getRecipe(
            @PathVariable Long familyId,
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user
    ) {
        RecipeResponseDTO.RecipeDetailDTO result = recipeService.getRecipeDetail(familyId, recipeId, user);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }

    @DeleteMapping("/{recipeId}")
    @Operation(summary = "가족 요리법 삭제")
    public ApiResponse<?> deleteRecipe(
            @PathVariable Long familyId,
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user
    ) {
        recipeService.deleteRecipe(familyId, recipeId, user);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @PostMapping("/{recipeId}/comments")
    @Operation(summary = "가족 요리법 댓글 등록")
    public ApiResponse<RecipeResponseDTO.recipeCommentDto> addRecipeComment(
            @PathVariable Long familyId,
            @PathVariable Long recipeId,
            @RequestBody RecipeRequestDTO.addRecipeCommentRequest request,
            @AuthenticationPrincipal User user
    ) {
        RecipeResponseDTO.recipeCommentDto result = commentService.save(familyId, recipeId, request, user);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }

    @PostMapping("/{recipeId}/re-comment")
    @Operation(summary="대댓글 달기")
    public ApiResponse<RecipeResponseDTO.recipeCommentDto> addReComment(
            @PathVariable Long familyId,
            @PathVariable Long recipeId,
            @AuthenticationPrincipal User user, @RequestParam Long commentId,
            @RequestBody RecipeRequestDTO.addRecipeCommentRequest request) {
        RecipeResponseDTO.recipeCommentDto response=commentService.saveRecipeRecomment(request,familyId,recipeId,commentId,user);
        return ApiResponse.onSuccess(SuccessStatus._OK,response);
    }

    @GetMapping("/{recipeId}/comments")
    @Operation(summary = "가족 요리법 댓글 조회")
    public ApiResponse<RecipeCommentResponse> getRecipeComments(
            @PathVariable Long familyId,
            @PathVariable Long recipeId,
            @RequestParam(required = false) Long commentId,
            @PageableDefault(size = 6) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        RecipeCommentResponse response = commentService.showRecipeComments(recipeId, commentId, pageable);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "가족 요리법 댓글 삭제")
    public ApiResponse<?> deleteRecipeComment(
            @PathVariable Long familyId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user
    ) {
        commentService.deleteComment(commentId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
