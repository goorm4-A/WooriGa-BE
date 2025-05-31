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
    USER_INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "USER_4005", "로그인 경로가 규칙에 맞지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_404","해당 유저가 없습니다"),

    //이미지 관련 에러
    WRONG_INPUT_IMAGE(HttpStatus.BAD_REQUEST,"IMAGE_4001","파일이 비어있습니다."),
    WRONG_IMAGE_FORMAT(HttpStatus.BAD_REQUEST,"IMAGE_4002","업로드한 이미지 형식이 잘못되었습니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST,"IMAGE_4004","이미지 업로드 중 오류가 발생했습니다."),

    //FamilyMember관련 에러
    FAMILY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_MEMBER_404", "해당 유저는 해당 가족의 구성원이 아닙니다."),
    DIARY_PARTICIPANTS_ERROR(HttpStatus.BAD_REQUEST,"PARTICIPANTS_404","DiaryParticipants 필드 저장에 실패했어요");


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