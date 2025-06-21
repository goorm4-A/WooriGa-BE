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
import org.springframework.web.bind.annotation.*;
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
                    
                    요청(json 아니라 form-data 형식임!)
                    - name: 가족 그룹 명 (필수)
                    - image: 가족 그룹 이미지 (한 장만, 필수 아님 > 안 보내면 null 반환)
                    
                    응답
                    - 가족 그룹 id, 가족 그룹명, 가족 그룹 이미지 url, 가족그룹 초대코드 반환
                    - 자세한 필드 설명은 가족 그룹 조회 참고
                    """)
    public ApiResponse<?> createFamilyGroup(@AuthenticationPrincipal User principalUser,
                                            @RequestPart("name") String name,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyMemberService.createFamilyGroup(principalUser, name, image));
    }

    // 가족 그룹 조회
    @GetMapping
    @Operation(summary = "가족 그룹 조회",
    description = """
            응답
            
            - "familyId":  가족 그룸 아이디
            - "familyName": 가족 그룹 명
            - "familyImage": https://~ - 가족 그룸 url
            - "inviteCode": 초대코드 6자리
            - "familyMembers": 3명까지
            - "familyMemberId": 가족 그룹의 멤버 아이디
            - "familyMemberName": 가족 멤버 이름(= 유저 이름)
            - "familyMemberImage": 가족 멤버 이미지
            - "relation": 관계 (나, 엄마, 아빠, ...)
            - "totalCnt": 총 가족 수
            """)
    public ApiResponse<?> getFamilyGroup(@AuthenticationPrincipal User principalUser) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyMemberService.getFamilyGroup(principalUser));
    }

    // 가족 그룹 상세 조회
    @GetMapping("/{groupId}")
    public ApiResponse<?> getFamilyGroupDetail(@AuthenticationPrincipal User principalUser,
                                         @PathVariable Long groupId) {
        return ApiResponse.onSuccess(SuccessStatus._OK,null);
//                familyMemberService.getFamilyGroupDetail(principalUser, groupId));
    }
}
