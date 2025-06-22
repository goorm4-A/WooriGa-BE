package com.example.server.service.recipe;

import com.example.server.converter.RecipeConverter;
import com.example.server.domain.entity.*;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.dto.familyRecipe.RecipeResponseDTO;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.code.exception.handler.FamilyRecipeHandler;
import com.example.server.global.code.exception.handler.ImageHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.recipe.FamilyRecipeRepository;
import com.example.server.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyRepository familyRepository;
    private final FamilyRecipeRepository familyRecipeRepository;
    private final S3Service s3Service;

    @Transactional
    public void createRecipe(
            Long familyId,
            User user,
            RecipeRequestDTO.createRecipeDTO requestDTO,
            List<MultipartFile> coverImages,
            List<MultipartFile> stepImages
    ) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyRecipe familyRecipe = RecipeConverter.toFamilyRecipe(requestDTO, familyMember);
        familyRecipeRepository.save(familyRecipe);

        //커버 이미지 업로드
        if (coverImages != null) {
            try {
                List<String> urls = s3Service.uploadImages(coverImages, "family-recipe/cover-image");
                urls.forEach(url -> familyRecipe.addCoverImage(
                        RecipeConverter.toCookingImage(url, null, familyRecipe)));
            } catch (IOException e) {
                throw new ImageHandler(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }

        }
        // Step 이미지 순서대로 업로드 후 각 스텝에 할당
        List<String> stepUrls = new ArrayList<>();
        if (stepImages != null) {
            try {
                stepUrls = s3Service.uploadImages(stepImages, "family-recipe/step-image");
            } catch (IOException e) {
                throw new ImageHandler(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }

        int urlIdx = 0;
        for (RecipeRequestDTO.cookingStepDTO stepDTO : requestDTO.getSteps()) {
            CookingStep step = familyRecipe.addStep(stepDTO.getImageIndexes(), stepDTO.getDescription());
            if (urlIdx < stepUrls.size()) {
                step.addImage(RecipeConverter.toCookingImage(stepUrls.get(urlIdx++), step, familyRecipe));
            }
        }
    }

    @Transactional(readOnly = true)
    public RecipeResponseDTO.RecipeListResponse getRecipeList(
            Long familyId,
            User user,
            Long lastRecipeId,
            Pageable pageable
    ) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        Pageable pageRequest = PageRequest.of(0, pageable.getPageSize() + 1);

        List<FamilyRecipe> recipes =
                familyRecipeRepository.findByFamilyId(familyId, lastRecipeId, pageRequest);

        boolean hasNext = recipes.size() > pageable.getPageSize();
        List<FamilyRecipe> responseRecipes = hasNext ? recipes.subList(0, pageable.getPageSize()) : recipes;

        List<RecipeResponseDTO.RecipeInfoDTO> dtoList = responseRecipes.stream()
                .map(RecipeConverter::toRecipeInfoDTO)
                .collect(Collectors.toList());

        Long nextCursor = hasNext && !responseRecipes.isEmpty()
                ? responseRecipes.get(responseRecipes.size() - 1).getId()
                : null;

        return RecipeResponseDTO.RecipeListResponse.builder()
                .recipes(dtoList)
                .hasNext(hasNext)
                .nextCursor(nextCursor != null ? nextCursor.toString() : null)
                .build();
    }

    @Transactional(readOnly = true)
    public RecipeResponseDTO.RecipeDetailDTO getRecipeDetail(
            Long familyId,
            Long recipeId,
            User user
    ) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyRecipe recipe = familyRecipeRepository.findById(recipeId)
                .orElseThrow(() -> new FamilyRecipeHandler(ErrorStatus.FAMILY_RECIPE_NOT_FOUND));

        return RecipeConverter.toRecipeDetailDTO(recipe);
    }

    @Transactional
    public void deleteRecipe(Long familyId, Long recipeId, User user) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));

        familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyRecipe recipe = familyRecipeRepository.findById(recipeId)
                .orElseThrow(() -> new FamilyRecipeHandler(ErrorStatus.FAMILY_RECIPE_NOT_FOUND));

        if (!recipe.getFamilyMember().getFamily().getId().equals(familyId)) {
            throw new FamilyRecipeHandler(ErrorStatus.FAMILY_RECIPE_NOT_FOUND);
        }

        List<String> imageUrls = new ArrayList<>();
        recipe.getCoverImages().forEach(img -> imageUrls.add(img.getImageUrl()));
        recipe.getSteps().forEach(step ->
                step.getImages().forEach(img -> imageUrls.add(img.getImageUrl())));

        for (String url : imageUrls) {
            if (url == null) continue;
            int idx = url.indexOf(".com/");
            if (idx != -1) {
                String key = url.substring(idx + 5);
                s3Service.deleteFile(key);
            }
        }

        familyRecipeRepository.delete(recipe);
    }
}