package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LoginDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class LoginResponse {
        private UserDTO.UserInfoResponse userInfo;
        private String accessToken;
        private String refreshToken;
    }
}
