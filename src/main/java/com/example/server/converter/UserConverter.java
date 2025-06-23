package com.example.server.converter;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.FamilyInfoResponse;
import com.example.server.dto.user.LoginResponse;
import com.example.server.dto.user.MainResponse;
import com.example.server.dto.user.UserInfoResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;


public class UserConverter {

    public static UserInfoResponse toUserInfoResponse(User user) {
        return UserInfoResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .status(user.getStatus())
                .image(user.getImage())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .userFamilies(toFamilyInfoListResponse(user.getFamilyMembers()))
                .build();
    }

    private static List<FamilyInfoResponse> toFamilyInfoListResponse(List<FamilyMember> familyMembers) {
        return familyMembers.stream()
                .map(fm -> FamilyInfoResponse.builder()
                        .familyId(fm.getFamily().getId())
                        .name(fm.getFamily().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public static LoginResponse toLoginResponse(Long userId, String userName, String userEmail,
                                                String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .userId(userId)
                .name(userName)
                .email(userEmail)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static MainResponse toMainResponse(User user,
                                              List<Family> families,
                                              List<String> familyMemberImages,
                                              List<String> diaryImages,
                                              List<String> recipeImages) {

        // Family > String
        List<String> familyNames = families.stream()
                .map(Family::getName)
                .toList();

        // 최신 등록된 가족 그룹 이미지
        String latestFamilyImage = user.getFamilyMembers().stream()
                .filter(fm -> fm.getFamily() != null && fm.getFamily().getImage() != null)
                .sorted(Comparator.comparing(fm -> fm.getFamily().getId(), Comparator.reverseOrder()))
                .map(fm -> fm.getFamily().getImage())
                .findFirst()
                .orElse(null);

        // 오늘 이미지 전부 합치고 정제
        List<String> todayImages = Stream.concat(
                        Stream.concat(familyMemberImages.stream(), diaryImages.stream()),
                        recipeImages.stream()
                ).filter(img -> img != null && !img.trim().isEmpty())
                .toList();

        return MainResponse.builder()
                .userName(user.getName())
                .userImage(user.getImage())
                .familyNames(familyNames)
                .latestFamilyImage(latestFamilyImage)
                .todayImages(todayImages)
                .build();
    }
}
