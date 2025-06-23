package com.example.server.service.user;

import com.example.server.converter.UserConverter;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.UserStatus;
import com.example.server.dto.user.MainResponse;
import com.example.server.dto.user.UserInfoRequest;
import com.example.server.dto.user.UserInfoResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.diary.FamilyDiaryRepository;
import com.example.server.repository.recipe.FamilyRecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyDiaryRepository familyDiaryRepository;
    private final FamilyRecipeRepository familyRecipeRepository;

    // 유저 정보 조회
    public UserInfoResponse getUserInfo(User principalUser) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));
        return UserConverter.toUserInfoResponse(user);
    }

    // 유저 정보 수정
    @Transactional
    public UserInfoResponse updateUserInfo(User principalUser, UserInfoRequest infoRequest) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        user.updateInfo(infoRequest.getName(), infoRequest.getPhone(), infoRequest.getBirthDate());
        return UserConverter.toUserInfoResponse(user);
    }

    // 유저 상태 수정
    @Transactional
    public String updateUserStatus(User principalUser) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        if (user.getStatus() == UserStatus.ACTIVE) {
            // 활성 > 비활성
            user.setInactiveStatus();
            return "변경된 status 상태: " + user.getStatus() + "(30일 후 유저 삭제)";
        } else {
            // 비활성 > 활성
            user.setActiveStatus();
            return "변경된 status 상태: " + user.getStatus();
        }
    }

    // 유저 삭제
    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 삭제
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime now = LocalDateTime.now();
        userRepository.deleteInactiveUsers(now.minusDays(30)); // 30일 기준
    }

    // 메인 화면 조회
    public MainResponse getMain(User principalUser) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        // 유저가 속한 가족 그룹
        List<FamilyMember> familyMembers = familyMemberRepository.findAllByUser(user);
        List<Family> families = familyMembers.stream()
                .map(FamilyMember::getFamily)
                .distinct()
                .toList();

        // 유저가 오늘 등록한 가족 그룹원 이미지
        List<Long> familyIds = families.stream()
                .map(Family::getId)
                .toList();

        List<String> familyMemberImages = familyMemberRepository
                .findTodayFamilyMemberImages(familyIds, LocalDate.now());

        // 유저가 오늘 작성한 가족 일기 이미지
        List<String> diaryImages = familyDiaryRepository
                .findTodayDiaryImageUrls(user.getId(), LocalDate.now());

        // 유저가 오늘 작성한 가족 요리법 이미지
        List<String> recipeImages = familyRecipeRepository
                .findTodayRecipeImages(user.getId(), LocalDate.now());

        return UserConverter.toMainResponse(user, families, familyMemberImages, diaryImages, recipeImages);
    }
}
