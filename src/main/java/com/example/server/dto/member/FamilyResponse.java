package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyResponse {
    private Long familyGroupId;
    private String familyName;
    private String familyImage;
    private Integer inviteCode;
}
