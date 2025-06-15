package com.example.server.controller;


import com.example.server.dto.familyDiary.FamilyDiaryDto;
import com.example.server.dto.familyDiary.FamilyDiaryListDto;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.diary.FamilyDiaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/family-diary")
@RequiredArgsConstructor
public class FamilyDiaryController {

    private final FamilyDiaryService familyDiaryService;


    //swagger 상에서 작동하도록 수정해야..
    @PostMapping(value="",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="가족 일기 등록")
    public ApiResponse<FamilyDiaryResponseDto> createDiary(@RequestPart FamilyDiaryDto familyDiaryDto,
                                                           @RequestPart(value="image",required=false) List<MultipartFile> image) {
        FamilyDiaryResponseDto result=familyDiaryService.createDiary(familyDiaryDto,image);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);

    }

    @GetMapping("")
    @Operation(summary="특정 추억일기 조회")
    public ApiResponse<FamilyDiaryResponseDto> getDiary(@RequestParam Long diaryId){
        FamilyDiaryResponseDto result=familyDiaryService.getFamilyDiaryDto(diaryId);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }

    @GetMapping("/list")
    @Operation(summary="추억 목록 조회")
    public ApiResponse<List<FamilyDiaryListDto>> getDiaryList(@RequestParam Long familyId) {
        List<FamilyDiaryListDto> result=familyDiaryService.getFamilyDiaryListDto(familyId);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }



//    @PutMapping("")
//    @Operation(summary="가족 일기 수정")
//    public ApiResponse<FamilyDiaryResponseDto> updateDiary(@RequestPart FamilyDiaryDto familyDiaryDto,
//                                                           @RequestPart(value="image",required=false) List<MultipartFile> image){
//        FamilyDiaryResponseDto result=familyDiaryService.updateDiary(familyDiaryDto,image);
//        return ApiResponse.onSuccess(SuccessStatus._OK,result);
//    }



}
