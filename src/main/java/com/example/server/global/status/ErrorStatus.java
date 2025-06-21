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
    IMAGE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"IMAGE_4006","이미지 삭제 중 오류가 발생했습니다."),
    S3_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"IMAGE_4005","s3상에서 이미지를 삭제하는 중 오류가 발생했습니다."),

    //FamilyMember관련 에러
    FAMILY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_MEMBER_404", "해당 유저는 해당 가족의 구성원이 아닙니다."),
    DIARY_PARTICIPANTS_ERROR(HttpStatus.BAD_REQUEST,"PARTICIPANTS_404","DiaryParticipants 필드 저장에 실패했어요"),

    //Family Diary 관련 에러
    FAMILY_DIARY_NOT_FOUND(HttpStatus.NOT_FOUND,"FAMILY_DIARY_404","해당 일기를 찾을 수 없습니다."),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND,"TAG_404","해당 태그를 찾을 수 없어요"),
    DIARY_TAG_ERROR(HttpStatus.BAD_REQUEST,"TAG_4005","태그 설정에 오류가 발생했어요."),

    //댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"COMMENT_404","해당 댓글을 찾을 수 없어요."),

    //기념일 관련 에러
    ANNIVERSARY_NOT_FOUND(HttpStatus.NOT_FOUND,"ANNIVERSARY_404","해당 기념일을 찾을 수 없어요."),



    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT4001", "유효하지 않은 JWT 토큰입니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4002", "JWT 토큰이 만료되었습니다."),
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT4003", "Authorization 헤더에 토큰이 존재하지 않습니다."),
    JWT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JWT5001", "토큰 처리 중 예기치 않은 오류가 발생했습니다."),
    // Family ERROR
    FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_4001", "가족을 찾을 수 없습니다." ),

    FAMILYMEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILYMEMBER_4001", "해당 가족 구성원을 찾을 수 없습니다."),
    FAMILY_MEMBER_INVALID(HttpStatus.NOT_FOUND, "FAMILY_MEMBER_4002", "해당 가족 구성원이 속한 가족 그룹이 아닙니다."),
    FAMILYMOTTO_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILYMOTTO_4001", "좌우명을 찾을 수 없습니다."),
    FAMILY_RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY_RECIPE_404", "해당 요리법을 찾을 수 없습니다.");

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