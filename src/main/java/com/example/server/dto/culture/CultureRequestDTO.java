package com.example.server.dto.culture;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CultureRequestDTO {

    @Getter
    public static class MottoRequestDTO {
        String familyName;
        @NotBlank(message = "좌우명을 입력하십시오")
        String motto;
    }
}
