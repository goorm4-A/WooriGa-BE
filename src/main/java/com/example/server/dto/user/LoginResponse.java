package com.example.server.dto.user;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LoginResponse {
    private Long userId;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
}

