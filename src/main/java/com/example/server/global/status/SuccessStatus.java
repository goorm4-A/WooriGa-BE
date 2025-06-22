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
    LOGIN_SUCCESSFUL(HttpStatus.OK, "LOGIN_2001", "로그인 성공입니다."),

    // 유저 관련 API
    USER_RETRIEVAL_SUCCESSFUL(HttpStatus.OK, "USER_2001", "유저 정보 조회 성공입니다."),
    USER_UPDATE_SUCCESSFUL(HttpStatus.OK, "USER_2002", "유저 정보 수정 성공입니다."),

    // 문화 API
    CREATE_MOTTO_SUCCESSFUL(HttpStatus.OK, "CULTURE_2001", "좌우명을 생성하였습니다."),
    CREATE_RULE_SUCCESSFUL(HttpStatus.OK, "CULTURE_2002", "약속/규칙을 생성하였습니다."),
    // 분위기 태그 API
    CREATE_MOOD_SUCCESSFUL(HttpStatus.OK, "MOOD_2001", "가족 분위기를 등록하였습니다."),
    // 가족사 관련 API
    CREATE_EVENT_SUCCESSFUL(HttpStatus.OK, "EVENT_2001", "가족사를 등록하였습니다."),
    // 가족 관련 API
    FAMILY_GROUP_UPDATE_SUCCESSFUL(HttpStatus.OK, "FAMILY_2001", "가족 그룹 정보 수정 성공입니다."),
    FAMILY_MEMBER_UPDATE_SUCCESSFUL(HttpStatus.OK, "FAMILY_2002", "가족 구성원 정보 수정 성공입니다.");

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