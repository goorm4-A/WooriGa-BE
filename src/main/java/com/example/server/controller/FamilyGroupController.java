package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.family.FamilyGroupService;
import com.example.server.service.family.FamilyMemberService;
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
public class FamilyGroupController {

    private final FamilyGroupService familyGroupService;

    // 가족 그룹 조회
    @GetMapping
    @Operation(summary = "가족 그룹 조회 API",
    description = """
            응답
            
            - "familyGroupId":  가족 그룸 아이디
            - "familyName": 가족 그룹 명
            - "familyImage": https://~ - 가족 그룸 url
            - "inviteCode": 초대코드 6자리
            - "familyMembers": 3명까지
            - "familyMemberId": 가족 그룹의 멤버 아이디
            - "familyMemberName": 가족 멤버 이름(= 유저 이름)
            - "familyMemberImage": 가족 멤버 이미지
            - "relation": 관계 (나, 엄마, 아빠, ...)
            - "birthDate": 생년월일
            - "isUserAdded": 유저 직접 추가 여부 (false는 기본 구성원)
            - "totalCnt": 총 가족 수
            """)
    public ApiResponse<?> getFamilyGroup(@AuthenticationPrincipal User principalUser) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyGroupService.getFamilyGroup(principalUser));
    }

    // 가족 그룹 상세 조회 (가계도)
    @GetMapping("/{groupId}/tree")
    @Operation(summary = "가족 그룹 상세 조회 API - 가계도 조회",
            description = """
            요청
            - groupId: 가족 그룹 아이디
            
            응답
            - "familyMemberId": 가족 그룹의 멤버 아이디
            - "familyMemberName": 가족 멤버 이름 (= 유저 이름, null 가능)
            - "familyMemberImage": 가족 멤버 이미지 (null 가능)
            - "relation": 관계 (나, 엄마, 아빠, ...)
            - "birthDate": 생년월일 (null 가능)
            - "isUserAdded": 유저 직접 추가 여부 (false는 기본 구성원)
            """)
    public ApiResponse<?> getFamilyGroupDetail(@AuthenticationPrincipal User principalUser,
                                         @PathVariable Long groupId) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyGroupService.getFamilyGroupDetail(principalUser, groupId));
    }

    // 가족 그룹 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가족 그룹 생성 API",
            description = """
                    가족 그룹 생성 시, 생성한 사람이 memberId 1 이고 기본 구성원 6명 생성
                    
                    요청 (json 아니라 form-data 형식임!)
                    - name: 가족 그룹 명 (필수)
                    - image: 가족 그룹 이미지 (한 장만, 필수 아님 > 안 보내면 null 반환)
                    
                    응답
                    - 가족 그룹 id, 가족 그룹명, 가족 그룹 이미지 url, 가족그룹 초대코드 반환
                    - 자세한 필드 설명은 가족 그룹 조회 참고
                    """)
    public ApiResponse<?> createFamilyGroup(@AuthenticationPrincipal User principalUser,
                                            @RequestParam("name") String name,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyGroupService.createFamilyGroup(principalUser, name, image));
    }

    // 가족 그룹 수정
    @PutMapping(value = "/{groupId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가족 그룹 수정 API",
            description = """   
                    전체적으로 가족 그룹 생성 API와 동일. 단 초대코드는 바뀌지 않음.
                    
                    요청 (json 아니라 form-data 형식임!)
                    - groupId: 가족 그룹 아이디
                    - name: 가족 그룹 명 (필수)
                    - image: 가족 그룹 이미지 (한 장만, 필수 아님 > 안 보내면 null 반환)
                    """)
    public ApiResponse<?> updateFamilyGroup(@AuthenticationPrincipal User principalUser,
                                            @PathVariable Long groupId,
                                            @RequestParam("name") String name,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.onSuccess(SuccessStatus.FAMILY_GROUP_UPDATE_SUCCESSFUL,
                familyGroupService.updateFamilyGroup(principalUser, groupId, name, image));
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "가족 그룹 삭제 API",
            description = """
                    가족 그룹 아래 가족 구성원(1), 가족 좌우명 및 규칙(2), 가족 일기(3) 모두 삭제 
                    (삭제 후, 가족 그룹 조회 API 에서 확인 가능)
                    
                    요청
                    - groupId: 가족 그룹 아이디
                    """)
    public ApiResponse<?> deleteFamilyGroup(@AuthenticationPrincipal User principalUser,
                                            @PathVariable Long groupId) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyGroupService.deleteFamilyGroup(principalUser, groupId));
    }
}
