package com.example.server.converter;

import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.FamilyInfoResponse;
import com.example.server.dto.user.LoginResponse;
import com.example.server.dto.user.UserInfoResponse;

import java.util.List;
import java.util.stream.Collectors;


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
}
