package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FamilyGroupResponse {
    private FamilyResponse familyGroup;
    private List<FamilyNotPositionResponse> familyMembers;
    private Integer totalCnt;
}

