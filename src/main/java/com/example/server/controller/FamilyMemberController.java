package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.member.FamilyMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "FamilyGroup", description = "가족 그룹 관련 기능")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    @PostMapping(value ="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가족 그룹 생성",
            description = """
                    가족 그룹 생성 시, 생성한 사람이 memberId 1
                    
                    요청
                    - name: 가족 그룹 명 (필수)
                    - image: 가족 그룹 이미지 (한 장만, 필수 아님)
                    
                    응답
                    - 가족 그룹 id, 가족 그룹명, 가족 그룹 이미지 url, 가족그룹 초대코드 반환
                    """)
    public ApiResponse<?> createFamilyGroup(@AuthenticationPrincipal User principalUser,
                                            @RequestPart("name") String name,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.onSuccess(SuccessStatus._OK, familyMemberService.createFamilyGroup(principalUser, name, image));
    }
}
