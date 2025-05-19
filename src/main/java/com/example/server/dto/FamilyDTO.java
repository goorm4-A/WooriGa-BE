package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class FamilyDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class FamilyInfoResponse {
        private Long familyId;
        private String name;
    }
}
