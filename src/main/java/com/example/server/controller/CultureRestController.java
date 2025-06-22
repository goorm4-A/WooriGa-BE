package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.dto.culture.CultureResponseDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.culture.CultureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/cultures")
@Slf4j
@Tag(name = "Culture", description = "좌우명, 요리법, 약속, 가족 분위기 등이 포함된 기능")
public class CultureRestController {
    private final CultureService cultureService;

    /*Todo 가훈 좌우명
     * : 사용자 로그인 권한 추가 필요
     * */
    @PostMapping("/motto/{familyId}")
    @Operation(summary = "좌우명 생성 API")
    public ApiResponse<?> createMotto(
            @Valid @RequestBody CultureRequestDTO.MottoRequestDTO mottoRequestDTO,
            @AuthenticationPrincipal User user,
            @PathVariable Long familyId) { // 임의 생성
        cultureService.createMotto(mottoRequestDTO, user.getId(), familyId);
        return ApiResponse.onSuccess(SuccessStatus.CREATE_MOTTO_SUCCESSFUL);
    }

    @GetMapping("/motto/{familyId}")
    @Operation(summary = "좌우명 조회 API")
    public ApiResponse<CultureResponseDTO.MottoListResponseDTO> getMottoList(
            @AuthenticationPrincipal User user,
            @PathVariable Long familyId,
            @RequestParam(required = false) String cursor
    ) {
        CultureResponseDTO.MottoListResponseDTO mottoList = cultureService.getMottoList(familyId, cursor, user.getId());
        return ApiResponse.onSuccess(mottoList);
    }

    @PatchMapping("/motto/{familyId}/{mottoId}")
    @Operation(summary = "좌우명 수정 API")
    public ApiResponse<CultureResponseDTO.MottoResponseDTO> updateMotto(
            @PathVariable Long mottoId,
            @Valid @RequestBody CultureRequestDTO.MottoRequestDTO requestDTO,@AuthenticationPrincipal User user
    ) {
        CultureResponseDTO.MottoResponseDTO responseDTO = cultureService.updateMotto(mottoId, requestDTO, user.getId());
        return ApiResponse.onSuccess(responseDTO);
    }


    @DeleteMapping("/motto/{mottoId}")
    @Operation(summary = "좌우명 삭제 API")
    public ApiResponse<String> deleteMotto(@PathVariable Long mottoId,
                                           @AuthenticationPrincipal User user
    ) {
        cultureService.deleteMotto(mottoId, user.getId());
        return ApiResponse.onSuccess("좌우명이 삭제되었습니다.");
    }

    @PostMapping("/rule")
    @Operation(summary = "약속 생성 API")
    public ApiResponse<?> createRule(@Valid @RequestBody CultureRequestDTO.RuleRequestDTO requestDTO,
                                     @AuthenticationPrincipal User user) {
        cultureService.createRule(requestDTO, user.getId());
        return ApiResponse.onSuccess(SuccessStatus.CREATE_RULE_SUCCESSFUL);
    }

    @GetMapping("/{familyId}/rule")
    @Operation(summary = "약속 페이지 조회 API")
    public ApiResponse<CultureResponseDTO.RuleListResponseDTO> getRuleList(
            @AuthenticationPrincipal User user,
            @PathVariable Long familyId,
            @RequestParam(required = false) String cursor
    ) {
        return ApiResponse.onSuccess(cultureService.getRuleList(familyId, cursor, user.getId()));
    }

    @GetMapping("/rule/{ruleId}")
    @Operation(summary = "약속 상세 조회 API")
    public ApiResponse<CultureResponseDTO.RuleResponseDTO> getRule(
            @PathVariable Long ruleId, @AuthenticationPrincipal User user
    ) {
        return ApiResponse.onSuccess(cultureService.getRule(ruleId, user.getId()));
    }
    @PatchMapping("rule/{ruleId}")
    @Operation(summary = "약속 수정 API")
    public ApiResponse<CultureResponseDTO.RuleResponseDTO> updateRule(
            @PathVariable Long ruleId,
            @Valid @RequestBody CultureRequestDTO.RuleRequestDTO requestDTO,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.onSuccess(cultureService.updateRule(ruleId, requestDTO, user.getId()));
    }

    @DeleteMapping("rule/{ruleId}")
    @Operation(summary = "약속 삭제 API")
    public ApiResponse<String> deleteRule(@PathVariable Long ruleId,@AuthenticationPrincipal User user) {
        cultureService.deleteRule(ruleId, user.getId());
        return ApiResponse.onSuccess("약속/규칙이 삭제되었습니다.");
    }
}
