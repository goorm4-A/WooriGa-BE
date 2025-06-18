package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.login.LoginService;
import com.example.server.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 유저 조회(마이페이지)
    @GetMapping("/me/info")
    @Operation(summary = "유저 정보 조회 API - 마이페이지",
            description = """
                    - "userId": 2 - 유저 아이디
                    - "name": "남유민" - 유저 이름
                    - "image": "https://~" - 프로필 이미지
                    - "phone": "010-xxxx-xxxx(첫 로그인 유저 null 반환 가능)" - 휴대폰 번호
                    - "userFamilies": [ \n
                        "familyId" : 1, \n
                        "name" : "가족그룹이름" \n
                        (첫 로그인 유저 빈리스트 반환 가능)] - 가족 그룹 리스트
                    """)
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal User PrincipalUser) {
        return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESSFUL, userService.getUserInfo(PrincipalUser));
    }
}
