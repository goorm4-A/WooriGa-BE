package com.example.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AnniversaryType {
    EVENTS("경조사"),
    ANNIVERSARY("기념일"),
    PROMISE("모임/약속"),
    ETC("기타");

    private final String displayName; //한글 이름을 저장하는 필드

    AnniversaryType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static AnniversaryType fromDisplayName(String displayname) {
        for (AnniversaryType type:AnniversaryType.values()){
            if(type.getDisplayName().equals(displayname)){
                return type;
            }
        }
        throw new IllegalStateException("존재하지 않은 기념일 유형입니다:"+displayname);
    }

}
