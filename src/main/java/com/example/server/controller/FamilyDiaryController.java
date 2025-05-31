package com.example.server.controller;

import com.example.server.domian.entity.FamilyDiary;
import com.example.server.dto.familyDiary.FamilyDiaryDto;
import com.example.server.dto.familyDiary.FamilyDiaryListDto;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.FamilyDiaryService;
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

    @PostMapping(value="",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="가족 일기 등록")
    public ApiResponse<FamilyDiaryResponseDto> createDiary(@RequestPart FamilyDiaryDto familyDiaryDto,
                                                           @RequestPart(value="image",required=false) List<MultipartFile> image) {
        FamilyDiaryResponseDto result=familyDiaryService.createDiary(familyDiaryDto,image);
        return ApiResponse.of(SuccessStatus._OK,result);

    }

//    @GetMapping("/family-diary")
//    @Operation(summary="가족별 추억일기 조회")
//    public ApiResponse<FamilyDiaryListDto> getDiaryList(@RequestParam Long familyId){
//        FamilyDiaryListDto result=familyDiaryService.getDiaryList(familyId);
//        return ApiResponse.of(SuccessStatus._OK,result);
//    }

}
