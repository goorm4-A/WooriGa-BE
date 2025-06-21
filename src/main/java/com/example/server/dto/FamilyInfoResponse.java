package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class FamilyInfoResponse {
    private Long familyId;
    private String name;
}
