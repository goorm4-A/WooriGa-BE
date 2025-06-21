package com.example.server.service.recipe;

import com.example.server.converter.RecipeConverter;
import com.example.server.domain.entity.*;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
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
import java.util.Map;

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
            Map<Integer, List<MultipartFile>> stepImages
    ) throws IOException {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        // TODO : 유저가 해당 가족 구성원으로 포함되어 있는 지 확인
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        FamilyRecipe familyRecipe = RecipeConverter.toFamilyRecipe(requestDTO, familyMember);
        familyRecipeRepository.save(familyRecipe);

        // 1) 커버 이미지 업로드
        List<String> coverUrls = s3Service.upload(coverImages);
        for (String url : coverUrls) {
            familyRecipe.addCoverImage(new CookingImage(url, null)); // step=null 이거나 별도 커버용 엔티티
        }

        // 각 Step에 이미지 업로드 & CookingImage 생성
        List<CookingStep> steps = RecipeConverter.toCookingSteps(requestDTO.getSteps());
        for (CookingStep step : steps) {
            step.setRecipe(familyRecipe);
            List<MultipartFile> files = stepImages.get(step.getStepIndex());
            if (files != null) {
                List<String> urls = s3Service.upload(files);
                for (String url : urls) {
                    CookingImage img = RecipeConverter.toCookingImage(url, step);
                    step.addImage(img);
                }
            }
            familyRecipe.addStep(step);
        }

    }
}
