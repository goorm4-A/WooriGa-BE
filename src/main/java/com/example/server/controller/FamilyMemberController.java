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

import java.time.LocalDate;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "FamilyGroup", description = "가족 그룹 관련 기능")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

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
                                            @RequestPart("name") String name,
                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyMemberService.createFamilyGroup(principalUser, name, image));
    }

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
                familyMemberService.getFamilyGroup(principalUser));
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
                familyMemberService.getFamilyGroupDetail(principalUser, groupId));
    }

    @PostMapping(value ="/{groupId}/members", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "가족 구성원 생성 API",
            description = """
                    새 구성원 반영된 전체 구성원 보려면 groups/{groupId}/tree 에서 확인
                    
                    요청 (json 아니라 form-data 형식임!)
                    - groupId: 가족 그룹 아이디
                    - name: 가족 그룹 명
                    - relation: 가족 관계
                    - birthDate: 생년월일(문자열, yyyy-mm-dd 형식으로 보내주세요)
                    - image: 가족 멤버 이미지 (한 장만, 필수 아님 > 안 보내면 null 반환)
                    
                    응답
                    - "familyMemberId": 가족 그룹의 멤버 아이디
                    - "familyMemberName": 가족 멤버 이름
                    - "familyMemberImage": 가족 멤버 이미지
                    - "relation": 관계 (큰엄마, 큰아빠, ...)
                    - "birthDate": 생년월일
                    - "isUserAdded": 유저 직접 추가 여부 (무조건 true)
                    """)
    public ApiResponse<?> createFamilyMember(@AuthenticationPrincipal User principalUser,
                                             @PathVariable Long groupId,
                                             @RequestPart("name") String name,
                                             @RequestPart("relation") String relation,
                                             @RequestPart("birthDate") String birthDateStr,
                                             @RequestPart(value = "image", required = false) MultipartFile image) {
        // LocalDate로 파싱
        LocalDate birthDate = LocalDate.parse(birthDateStr);

        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyMemberService.createFamilyMember(principalUser, groupId, name, relation, birthDate, image));
    }

    // 가족 구성원 상세 조회
    @GetMapping("/{groupId}/members/{memberId}")
    @Operation(summary = "가족 구성원 상세 조회 API",
            description = """
            요청
            - groupId: 가족 그룹 아이디
            - memberId: 가족 구성원 아이디
            
            응답
            - "familyMemberName": 가족 멤버 이름
            - "familyMemberImage": 가족 멤버 이미지
            - "relation": 관계 (큰엄마, 큰아빠, ...)
            - "birthDate": 생년월일
            """)
    public ApiResponse<?> getFamilyMemberDetail(@AuthenticationPrincipal User principalUser,
                                                @PathVariable Long groupId,
                                                @PathVariable Long memberId) {
        return ApiResponse.onSuccess(SuccessStatus._OK,
                familyMemberService.getFamilyMemberDetail(principalUser, groupId, memberId));
    }
}
