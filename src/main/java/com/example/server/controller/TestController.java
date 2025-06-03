package com.example.server.controller;

import com.example.server.dto.TestDTO;
import com.example.server.global.ApiResponse;
import com.example.server.global.status.SuccessStatus;
import com.example.server.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    ApiResponse<TestDTO> geteTest(@RequestParam Integer id) {
        TestDTO result = testService.getTestById(id);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }

    @PostMapping
    ApiResponse<TestDTO> postTest(@RequestParam Integer id) {
        TestDTO result = testService.getTestById(id);
        return ApiResponse.onSuccess(SuccessStatus._OK, result);
    }
}
