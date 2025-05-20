package com.example.server.global.status;

import com.example.server.global.code.BaseErrorCode;
import com.example.server.global.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 공통 에러
    COMMON_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러가 발생했습니다. 관리자에게 문의하세요."),
    COMMON_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_4001", "잘못된 요청입니다."),
    COMMON_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_4002", "인증이 필요합니다."),

    // 유저 에러
    USER_INVALID_PROVIDER(HttpStatus.UNAUTHORIZED, "USER", "잘못된 로그인 접근 경로 입니다."),

    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT4001", "유효하지 않은 JWT 토큰입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4002", "JWT 토큰이 만료되었습니다."),
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT4003", "Authorization 헤더에 토큰이 존재하지 않습니다."),
    JWT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JWT5001", "토큰 처리 중 예기치 않은 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return getReason();
    }

}