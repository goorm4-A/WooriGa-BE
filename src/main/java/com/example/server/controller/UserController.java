package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.user.UserInfoRequest;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 기능")
public class UserController {

    private final UserService userService;

    // 사용자 정보 조회 (마이페이지)
    @GetMapping("/me/info")
    @Operation(summary = "유저 정보 조회 API - 마이페이지",
            description = """
                    응답 형식
                    - "userId": 2 - 유저 아이디
                    - "name": "김박수" - 유저 이름
                    - "status": "ACTIVE" - 계정 상태
                    - "image": "https://~" - 프로필 이미지
                    - "phone": "010-xxxx-xxxx(첫 로그인 유저 null 반환 가능)" - 휴대폰 번호
                    - "birthDate": "2025-06-18" - 출생 날짜
                    - "userFamilies": [ \n
                        "familyId" : 1, \n
                        "name" : "가족그룹이름" \n
                        (첫 로그인 유저 빈리스트 반환 가능)] - 가족 그룹 리스트
                    """)
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal User PrincipalUser) {
        return ApiResponse.onSuccess(SuccessStatus.USER_RETRIEVAL_SUCCESSFUL, userService.getUserInfo(PrincipalUser));
    }

    // 사용자 정보 수정 (마이페이지)
    @PutMapping("/me/info")
    @Operation(summary = "유저 정보 수정 API - 마이페이지",
            description = """
                    요청 형식
                    
                    - "name": "남유민" - 유저 이름
                    - "phone": "010-xxxx-xxxx(첫 로그인 유저 null 반환 가능)" - 휴대폰 번호
                    - "birthDate": "2025-06-18" - 출생 날짜
                    """)
    public ApiResponse<?> updateUserInfo(@AuthenticationPrincipal User principalUser,
                                         @Valid @RequestBody UserInfoRequest request) {
        return ApiResponse.onSuccess(SuccessStatus.USER_UPDATE_SUCCESSFUL, userService.updateUserInfo(principalUser, request));
    }

    // 사용자 상태 변경
    @PatchMapping("/me/status")
    @Operation(summary = "유저 status 변경 및 30일 후 삭제 API",
            description = """
                    응답 형식
                    - 변경된 status 상태: ACTIVE 또는 INACTIVE(30일 후 유저 삭제)
                    
                    INACTIVE의 경우 30일 후 자동 삭제""")
    public ApiResponse<?> updateUserStatus(@AuthenticationPrincipal User principalUser) {
        return ApiResponse.onSuccess(SuccessStatus.USER_UPDATE_SUCCESSFUL, userService.updateUserStatus(principalUser));
    }

    @GetMapping("/main")
    @Operation(summary = "메인 화면 조회 API",
            description = """
                    응답
                    
                    - "userName": 로그인 유저 이름
                    - "userImage": 프로필 이미지
                    - "familyNames": [ 가족 그룹명 이름 리스트 ]
                    - "latestFamilyImage": 등록한 가족 그룹 중 이미지가 있는 최근 그룹
                    - "todayImages": [ 오늘 등록한 이미지 (from 가족 구성원 이미지 + 가족 일기 이미지 + 가족 요리법 이미지) ]
                    """)
    public ApiResponse<?> getMain(@AuthenticationPrincipal User principalUser) {
        return ApiResponse.onSuccess(SuccessStatus.USER_UPDATE_SUCCESSFUL, userService.getMain(principalUser));
    }
}
