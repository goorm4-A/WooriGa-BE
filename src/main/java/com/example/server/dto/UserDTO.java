package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class UserDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserInfoResponse {
        private Long userId;
        private String name;
        private String nickname;
        private String image;
        private List<FamilyDTO.FamilyInfoResponse> userFamilies;
    }
}
