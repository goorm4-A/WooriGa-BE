package com.example.server.global.code.exception;

import com.example.server.global.ApiResponse;
import com.example.server.global.status.ErrorStatus;
import org.springframework.http.ResponseEntity;

public class CustomException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }

    public static <T> ResponseEntity<ApiResponse<T>> createErrorResponse(ErrorStatus errorStatus, T data) {
        return ResponseEntity
                .status(errorStatus.getHttpStatus())
                .body(ApiResponse.onFailure(errorStatus, data));
    }
}