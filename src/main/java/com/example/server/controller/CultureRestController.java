package com.example.server.controller;


import com.example.server.domian.entity.User;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.global.ApiResponse;
import com.example.server.service.culture.CultureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/culture")
@Slf4j
@Tag(name = "Culture", description = "")
public class CultureRestController {
    private final CultureService cultureService;

    /*Todo 가훈 좌우명 등록
     * : 사용자 로그인 권한 추가 필요
     * */
    @PostMapping("/motto")
    public ApiResponse<String> createMotto(@Valid @RequestBody CultureRequestDTO.MottoRequestDTO mottoRequestDTO, Long userId) { // 임의 생성
        cultureService.createMotto(mottoRequestDTO, userId);
        return ApiResponse.onSuccess("좌우명이 생성되었습니다.");
    }

    @DeleteMapping("/motto/{mottoId}")
    public ApiResponse<String> deleteMotto(@PathVariable Long mottoId, Long userId) {
        cultureService.deleteMotto(mottoId, userId);
        return ApiResponse.onSuccess("좌우명이 삭제되었습니다.");
    }
}
