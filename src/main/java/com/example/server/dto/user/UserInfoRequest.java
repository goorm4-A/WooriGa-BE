package com.example.server.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

// 정보 수정
@Getter
public class UserInfoRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotNull
    private LocalDate birthDate;
}
