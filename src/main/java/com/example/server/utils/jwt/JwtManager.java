package com.example.server.utils.jwt;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtManager {

    private final JwtUtil jwtUtil;

    // accessToken 발급
    public String createAccessToken(Long userId) {
        return jwtUtil.createAccessToken(userId);
    }

    // refreshToken 발급
    public String createRefreshToken(Long userId) {
        return jwtUtil.createRefreshToken(userId);
    }

    // accessToken 검증
    public Long validateAccessToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // refreshToken 검증
    public Long validateRefreshToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // Authorization 헤더에서 토큰 추출
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        throw new CustomException(ErrorStatus.JWT_NOT_FOUND);
    }
}
