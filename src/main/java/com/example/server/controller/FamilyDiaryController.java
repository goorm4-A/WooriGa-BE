package com.example.server.controller;


import com.example.server.dto.familyDiary.*;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.diary.FamilyDiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    //familyDiaryId:마지막으로 조회한 다이어리 id, familyId: 쿼리문의 where 절을 위해
    public ApiResponse<FamilyDiaryScrollResponse> getDiaryList(@Parameter(description="무한 스크롤") @PageableDefault(size=6) Pageable pageable,
                                                               @RequestParam(value="familyId")Long familyId,
                                                               @Parameter(description = "조회한 마지막 diaryId") @RequestParam(value="familyDiaryId",required = false) Long familyDiaryId) {
        FamilyDiaryScrollResponse result=familyDiaryService.getFamilyDiaryListDto(familyId,familyDiaryId,pageable);
        return ApiResponse.onSuccess(SuccessStatus._OK,result);
    }

    @DeleteMapping("")
    @Operation(summary = "특정 가족 일기 삭제")
    public ApiResponse deleteDiary(@RequestParam Long diaryId) {
        familyDiaryService.deleteDiary(diaryId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }


    //✏️페이징 or 무한 스크롤 방식 결정 후 수정해야 함
    @GetMapping("/search")
    @Operation(summary="일기 제목으로 검색")
    public ApiResponse<List<FamilyDiaryListDto>> searchDiary(@RequestParam Long familyId,@RequestParam String keyword) {
        List<FamilyDiaryListDto> results=familyDiaryService.searchFamilyDiary(keyword,familyId);
        return ApiResponse.onSuccess(SuccessStatus._OK,results);
    }

//    @PostMapping("/comment")
//    @Operation(summary="가족 일기에 댓글 달기")
//    public ApiResponse<CommentDto> addComment(@RequestParam Long diaryId, @RequestParam String comment) {
//
//    }



//    @PutMapping("")
//    @Operation(summary="가족 일기 수정")
//    public ApiResponse<FamilyDiaryResponseDto> updateDiary(@RequestPart FamilyDiaryDto familyDiaryDto,
//                                                           @RequestPart(value="image",required=false) List<MultipartFile> image){
//        FamilyDiaryResponseDto result=familyDiaryService.updateDiary(familyDiaryDto,image);
//        return ApiResponse.onSuccess(SuccessStatus._OK,result);
//    }



}
