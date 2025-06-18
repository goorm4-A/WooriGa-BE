package com.example.server.dto.user;

import com.example.server.dto.FamilyInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
public class UserInfoResponse {
    private Long userId;
    private String name;
    private String image;
    private String phone;
    private LocalDateTime birthDateTime;
    private List<FamilyInfoResponse> userFamilies;
}