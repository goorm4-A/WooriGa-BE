package com.example.server.dto.user;

import com.example.server.domain.enums.UserStatus;
import com.example.server.dto.FamilyInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// 정보 조회
@Builder
@Getter
public class UserInfoResponse {
    private Long userId;
    private String name;
    private UserStatus status;
    private String image;
    private String phone;
    private LocalDateTime birthDateTime;
    private List<FamilyInfoResponse> userFamilies;
}