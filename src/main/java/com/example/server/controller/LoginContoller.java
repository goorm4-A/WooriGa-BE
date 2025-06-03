package com.example.server.controller;

import com.example.server.dto.LoginDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class LoginContoller {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private final LoginService loginService;

    // 1. 카카오 로그인 창으로 이동
    // 로그인 성공 후 리다이렉트 URI로 인가 코드 보내줌
    @GetMapping("/redirect")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + client_id
                + "&redirect_uri=" + redirect_uri
                + "&response_type=code";
        response.sendRedirect(kakaoUrl);
    }

    // 2. 인가코드 받아 로그인 진행
    @GetMapping("/kakao")
    @Operation(summary = "카카오 로그인 API",
            description = "유저 정보, Access Token, Refresh Token 응답")
    public ApiResponse<?> loginWithKakao(@RequestParam String code) {
        LoginDTO.LoginResponse loginResponse = getLoginResponse(code);
        return ApiResponse.onSuccess(SuccessStatus.LOGIN_SUCCESSFUL,loginResponse);
    }

    private LoginDTO.LoginResponse getLoginResponse(String code) {
        LoginDTO.LoginResponse loginResponse =  loginService.getKakaoAccessToken(code);
        return loginResponse;
    }
}
