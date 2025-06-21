package com.example.server.service.recipe;

import com.example.server.converter.RecipeConverter;
import com.example.server.domain.entity.*;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.code.exception.handler.ImageHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.recipe.FamilyRecipeRepository;
import com.example.server.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

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
        // 각 Step에 이미지 업로드 & CookingImage 생성
        for (RecipeRequestDTO.cookingStepDTO stepDTO : requestDTO.getSteps()) {
            CookingStep step = familyRecipe.addStep(stepDTO.getImageIndexes(), stepDTO.getDescription());
            // step별 이미지 필터링: stepImages 리스트에서 stepDto.order 기준 분리
            List<MultipartFile> imgFotStep = filterByOrder(stepImages, stepDTO.getImageIndexes());
            if (!imgFotStep.isEmpty()) {
                try {
                    log.info(imgFotStep.get(0).getOriginalFilename());
                    List<String> urls = s3Service.uploadImages(imgFotStep, "family-recipe/step-image");
                    urls.forEach(url -> step.addImage(
                            RecipeConverter.toCookingImage(url, step, familyRecipe)));
                } catch (IOException e) {
                    throw new ImageHandler(ErrorStatus.IMAGE_UPLOAD_ERROR);
                }
            }
        }
    }

    private List<MultipartFile> filterByOrder(List<MultipartFile> all, int order) {
        // 파일명 규칙 등으로 order별 필터링 로직 구현
        return all.stream()
                .filter(f -> f.getOriginalFilename().startsWith(order + "_"))
                .collect(Collectors.toList());
    }
}
