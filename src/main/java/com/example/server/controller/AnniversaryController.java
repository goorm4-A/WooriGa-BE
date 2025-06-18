package com.example.server.controller;

import com.example.server.dto.anniversary.AnniversaryRequest;
import com.example.server.dto.anniversary.AnniversaryResponse;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.anniversary.AnniversaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anniversary")
@RequiredArgsConstructor
public class AnniversaryController {

    private final AnniversaryService anniversaryService;

    @PostMapping(value="")
    @Operation(summary="가족 기념일 등록")
    public ApiResponse<AnniversaryResponse> createDiary(@RequestBody AnniversaryRequest familyDiaryDto) {
        AnniversaryResponse result=anniversaryService.createSchedule(familyDiaryDto); //security 적용 시 변경해야
        return ApiResponse.onSuccess(SuccessStatus._OK,result);

    }


}
