package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.anniversary.AnniversaryRequest;
import com.example.server.dto.anniversary.AnniversaryResponse;
import com.example.server.dto.anniversary.AnniversaryResponseList;
import com.example.server.dto.anniversary.AnniversaryScrollResponse;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.anniversary.AnniversaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anniversary")
@RequiredArgsConstructor
public class AnniversaryController {

    private final AnniversaryService anniversaryService;

    @PostMapping(value="")
    @Operation(summary="가족 기념일 등록")
    public ApiResponse<AnniversaryResponse> createDiary(@RequestBody AnniversaryRequest familyDiaryDto,@AuthenticationPrincipal User user) {
        AnniversaryResponse result=anniversaryService.createSchedule(familyDiaryDto,user); //security 적용 시 변경해야
        return ApiResponse.onSuccess(SuccessStatus._OK,result);

    }

    @GetMapping("/calendar")
    @Operation(summary="기념일 캘린더")
    //무한스크롤 X
    public ApiResponse<List<AnniversaryResponseList>> getAnniversaryList(
            @AuthenticationPrincipal User user,
            @RequestParam int year,
            @RequestParam int month){
        List<AnniversaryResponseList> result=anniversaryService.getMonthSchedule(user,year,month);
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

//    @GetMapping("/search")
//    @Operation(summary="기념일 타입으로 검색")
//    public ApiResponse<List<AnniversaryResponseList>> searchAnniversary(@RequestParam String type,@AuthenticationPrincipal User user){
//        List<AnniversaryResponseList> result=anniversaryService.searchAnniversary(type,user);
//        return ApiResponse.onSuccess(SuccessStatus._OK,result);
//    }

    @GetMapping("/search")
    @Operation(summary="기념일 전체 조회+태그로 검색")
    public ApiResponse<AnniversaryScrollResponse> getAllAnniversary(
            @AuthenticationPrincipal User user,
            @RequestParam(required=false) String type,
            @RequestParam(required=false) Long lastAnniversaryId,
            @PageableDefault(size=6) Pageable pageable){

        //타입이 있는 경우, 필터링 된 전체목록 조회
        if(type!=null&&!type.isEmpty()){
            AnniversaryScrollResponse result=anniversaryService.searchAnniversary(user,type,lastAnniversaryId,pageable);
            return ApiResponse.onSuccess(SuccessStatus._OK,result);
        }

        //타입이 없는 경우, 이벤트 전체조회
        AnniversaryScrollResponse results=anniversaryService.getAllAnniversary(user,lastAnniversaryId,pageable);
        return ApiResponse.onSuccess(SuccessStatus._OK,results);

    }

//    @GetMapping("/this-month/list")
//    @Operation(summary="이번 달 기념일 한 눈에 보기(D-DAY 함께 표시)")
//    //무한 스크롤 존재
//    public ApiResponse<AnniversaryResponseList> getThisMonthAnniversaryList(
//            @AuthenticationPrincipal User user,
//            @RequestParam(required=false)  Long lastAnniversaryId,
//            @PageableDefault(size=6) Pageable pageable){
//
//        AnniversaryScrollResponse result=anniversaryService.getAnniversaryDday(user,lastAnniversaryId,pageable);
//    }






}
