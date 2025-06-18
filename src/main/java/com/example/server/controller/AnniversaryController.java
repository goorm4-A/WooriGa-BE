package com.example.server.controller;

import com.example.server.dto.anniversary.AnniversaryRequest;
import com.example.server.dto.anniversary.AnniversaryResponse;
import com.example.server.dto.anniversary.AnniversaryResponseList;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.anniversary.AnniversaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/calendar")
    @Operation(summary="이번 달 기념일 한 눈에 보기")
    public ApiResponse<List<AnniversaryResponseList>> getAnniversaryList(
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month){
        List<AnniversaryResponseList> result=anniversaryService.getMonthSchedule(userId,year,month);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }

    @GetMapping("/detail")
    @Operation(summary="기념일 상세 보기")
    public ApiResponse<AnniversaryResponse> getAnniversaryDetail(
            @RequestParam Long anniversaryId
    ){
        AnniversaryResponse result=anniversaryService.getAnniversaryDetail(anniversaryId);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }

    @GetMapping("/search")
    @Operation(summary="기념일 타입으로 검색")
    public ApiResponse<List<AnniversaryResponseList>> searchAnniversary(@RequestParam String type,@RequestParam Long userId){
        List<AnniversaryResponseList> result=anniversaryService.searchAnniversary(type,userId);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }




}
