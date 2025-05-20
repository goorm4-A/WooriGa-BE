package com.example.server.global.status;

import com.example.server.global.code.BaseCode;
import com.example.server.global.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 공통 성공
    _OK(HttpStatus.OK, "COMMON_200", "성공입니다."),
    LOGIN_SUCCESSFUL(HttpStatus.OK, "LOGIN_2001", "로그인 성공입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return getReason();
    }
}