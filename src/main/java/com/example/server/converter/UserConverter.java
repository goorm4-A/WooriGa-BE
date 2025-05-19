package com.example.server.converter;

import com.example.server.domian.entity.FamilyMember;
import com.example.server.domian.entity.User;
import com.example.server.dto.FamilyDTO;
import com.example.server.dto.LoginDTO;
import com.example.server.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;


public class UserConverter {

    public static UserDTO.UserInfoResponse toUserInfoResponse(User user) {
        return UserDTO.UserInfoResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .image(user.getImage())
                .userFamilies(toFamilyInfoListResponse(user.getFamilyMembers()))
                .build();
    }

    private static List<FamilyDTO.FamilyInfoResponse> toFamilyInfoListResponse(List<FamilyMember> familyMembers) {
        return familyMembers.stream()
                .map(fm -> FamilyDTO.FamilyInfoResponse.builder()
                        .familyId(fm.getFamily().getId())
                        .name(fm.getFamily().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public static LoginDTO.LoginResponse toLoginResponse(UserDTO.UserInfoResponse userinfo,
                                                          String accessToken, String refreshToken) {
        return LoginDTO.LoginResponse.builder()
                .userInfo(userinfo)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
