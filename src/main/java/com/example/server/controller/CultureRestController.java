package com.example.server.controller;


import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.global.ApiResponse;
import com.example.server.service.culture.CultureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<MottoResponseDTO> createMotto(@Valid @RequestBody CultureRequestDTO.MottoRequestDTO mottoRequestDTO) {
        cultureService.createMotto(mottoRequestDTO);

    }

}
