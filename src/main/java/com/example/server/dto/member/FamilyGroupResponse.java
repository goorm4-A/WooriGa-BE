package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FamilyGroupResponse {
    private Long familyGroupId;
    private String familyGroupName;
    private String familyGroupImage;
    private Integer inviteCode;
}
