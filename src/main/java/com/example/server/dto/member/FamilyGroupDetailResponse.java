package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FamilyGroupDetailResponse {
    private List<FamilyMemberResponse> familyMembers;
}
