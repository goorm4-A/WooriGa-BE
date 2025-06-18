package com.example.server.dto.culture;

import com.example.server.domain.enums.RuleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CultureRequestDTO {

    @Getter
    public static class MottoRequestDTO {
        String familyName;
        @NotBlank(message = "좌우명을 입력하십시오")
        String motto;
    }

    @Getter
    public static class CreateRuleRequestDTO {
        String familyName;
        RuleType ruleType;
        String title;
        String description;
    }
}
