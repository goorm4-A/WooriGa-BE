package com.example.server.controller;

import com.example.server.domain.entity.User;
import com.example.server.dto.familyEvent.FamilyEventRequest;
import com.example.server.dto.familyEvent.FamilyEventResponse;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.event.FamilyEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
