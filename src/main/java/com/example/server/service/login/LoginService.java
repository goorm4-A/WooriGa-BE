package com.example.server.service.login;

import com.example.server.converter.UserConverter;
import com.example.server.domain.entity.User;
import com.example.server.dto.LoginDTO;
import com.example.server.dto.UserDTO;
import com.example.server.repository.UserRepository;
import com.example.server.utils.jwt.JwtManager;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private final UserRepository userRepository;
    private final JwtManager jwtManager;

    // 인가코드로부터 카카오 액세스 토큰 발급
    public LoginDTO.LoginResponse getKakaoAccessToken(String code) {

        // 카카오 로그인 api 이용하므로 WebClient 이용
        JsonNode tokenNode = WebClient.create("https://kauth.kakao.com")
                .post()
                .uri("/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", client_id)
                        .with("redirect_uri", redirect_uri)
                        .with("code", code))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String kAccessToken = tokenNode.get("access_token").asText();

        // 카카오 액세스 토큰으로부터 유저정보 추출
        JsonNode userInfoNode = getKakaoUserInfo(kAccessToken);

        String email = userInfoNode.get("kakao_account").get("email").asText();
        String nickname = userInfoNode.get("properties").get("nickname").asText();
        String profileImage = userInfoNode.get("properties").get("profile_image").asText();

        // 우리 db에 유저 없으면 유저 생성
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = userRepository.save(new User(email, nickname, profileImage));
        }

        Long userId = user.getId();
        String accessToken = jwtManager.createAccessToken(userId);
        String refreshToken = jwtManager.createRefreshToken(userId);

        user.setTokens(accessToken, refreshToken);
        userRepository.save(user);

        UserDTO.UserInfoResponse userInfo = UserConverter.toUserInfoResponse(user);
        return UserConverter.toLoginResponse(userInfo, accessToken, refreshToken);
    }

    public JsonNode getKakaoUserInfo(String kAccessToken) {

        return WebClient.create("https://kapi.kakao.com")
                .post()
                .uri("/v2/user/me")
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kAccessToken)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
