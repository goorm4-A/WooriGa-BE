package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.familyEvent.*;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.event.FamilyEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "FamilyEvent", description = "가족사 관련 기능")
@Slf4j
public class FamilyEventController {

    private final FamilyEventService familyEventService;

    @PostMapping("")
    @Operation(summary = "가족사 등록")
    public ApiResponse<FamilyEventResponse> createEvent(
            @AuthenticationPrincipal User user,
            @RequestBody FamilyEventRequest request) {

        FamilyEventResponse response = familyEventService.createFamilyEvent(user, request);
        return ApiResponse.onSuccess(SuccessStatus.CREATE_EVENT_SUCCESSFUL, response);
    }

    @GetMapping("")
    @Operation(summary = "가족사 타임라인 조회")
    public ApiResponse<List<FamilyEventTimelineDto>> getTimeline(
            @AuthenticationPrincipal User user,
            @RequestParam Long familyId) {
        List<FamilyEventTimelineDto> responses = familyEventService.getFamilyEventTimeline(user, familyId);
        return ApiResponse.onSuccess(SuccessStatus._OK, responses);
    }

    @GetMapping("/map")
    @Operation(summary = "지도 가족사 조회")
    public ApiResponse<List<FamilyEventResponse>> getEventsForMap(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) Long familyId) {
        List<FamilyEventResponse> responses = familyEventService.getFamilyEventsForMap(user, familyId);
        return ApiResponse.onSuccess(SuccessStatus._OK, responses);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "가족사 상세 조회")
    public ApiResponse<FamilyEventDetailDto> getEventDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        FamilyEventDetailDto response = familyEventService.getFamilyEventDetail(user, eventId);
        return ApiResponse.onSuccess(SuccessStatus._OK, response);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "가족사 수정")
    public ApiResponse<FamilyEventResponse> updateEvent(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId,
            @RequestBody FamilyEventUpdateRequest request) {
        FamilyEventResponse response = familyEventService.updateFamilyEvent(user, eventId, request);
        return ApiResponse.onSuccess(SuccessStatus.UPDATE_EVENT_SUCCESSFUL, response);
    }
}
