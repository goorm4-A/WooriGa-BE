package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    // 3. 로그인 테스트
    @GetMapping("user")
    public ApiResponse<?> getUserId(@AuthenticationPrincipal User user) {
        return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESSFUL, user.getId());
    }
}
