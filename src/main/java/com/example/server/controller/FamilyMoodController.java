package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.mood.MoodRequestDTO;
import com.example.server.dto.mood.MoodResponseDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.mood.MoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cultures/{familyId}/moods")
@RequiredArgsConstructor
@Tag(name = "Mood", description = "가족 분위기 태그 기능")
public class FamilyMoodController {
    private final MoodService moodService;

    @PostMapping
    @Operation(summary = "가족 분위기 등록")
    public ApiResponse<MoodResponseDTO> createMood(@PathVariable Long familyId,
                                                   @RequestBody MoodRequestDTO request,
                                                   @AuthenticationPrincipal User user){
        MoodResponseDTO result = moodService.createMood(user, familyId, request);
        return ApiResponse.onSuccess(SuccessStatus.CREATE_MOOD_SUCCESSFUL, result);
    }

    @GetMapping
    @Operation(summary = "가족 분위기 목록 조회")
    public ApiResponse<List<MoodResponseDTO>> getMoods(@PathVariable Long familyId){
        List<MoodResponseDTO> result = moodService.getFamilyMoods(familyId);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }
}
