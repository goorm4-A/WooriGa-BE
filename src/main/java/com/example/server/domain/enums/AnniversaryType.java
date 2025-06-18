package com.example.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AnniversaryType {
    EVENTS("경조사"),
    BIRTHDAY("생일"),
    PROMISE("약속"),
    ETC("기타");

    private final String displayName; //한글 이름을 저장하는 필드

    AnniversaryType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

}
