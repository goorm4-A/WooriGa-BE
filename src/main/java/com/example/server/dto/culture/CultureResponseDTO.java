package com.example.server.dto.culture;

import com.example.server.domain.enums.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CultureResponseDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MottoListResponseDTO {
        List<MottoResponseDTO> mottos;
        boolean hasNext;
        String nextCursor;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MottoResponseDTO {
        private Long id;
        private String title;
        private String familyName;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RuleListResponseDTO {
        List<RuleResponseDTO> rules;
        boolean hasNext;
        String nextCursor;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RuleResponseDTO {
        private Long id;
        private String title;
        private String familyName;
        private RuleType ruleType;
        private LocalDateTime createdAt;
    }
}
