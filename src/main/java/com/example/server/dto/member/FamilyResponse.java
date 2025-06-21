package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyResponse {
    private Long familyId;
    private String familyName;
    private String familyImage;
    private Integer inviteCode;
}
