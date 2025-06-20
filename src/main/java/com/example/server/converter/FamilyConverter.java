package com.example.server.converter;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.FamilyMotto;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.dto.member.FamilyGroupResponse;
import jakarta.validation.Valid;

public class FamilyConverter {
    public static FamilyMotto toFamilyMotto(CultureRequestDTO.MottoRequestDTO requestDTO, FamilyMember familyMember, Family family) {
        return new FamilyMotto().builder()
                .title(requestDTO.getMotto())
                .familyMember(familyMember)
                .family(family)
                .build();
    }

    public static FamilyMotto toFamilyRule(CultureRequestDTO.@Valid RuleRequestDTO requestDTO, FamilyMember familyMember, Family family) {
        return new FamilyMotto().builder()
                .title(requestDTO.getTitle())
                .familyMember(familyMember)
                .family(family)
                .description(requestDTO.getDescription())
                .ruleType(requestDTO.getRuleType())
                .build();
    }

    public static FamilyGroupResponse toFamilyGroupResponse(Family family) {
        return FamilyGroupResponse.builder()
                .familyGroupId(family.getId())
                .familyGroupName(family.getName())
                .inviteCode(family.getInviteCode())
                .familyGroupImage(family.getImage())
                .build();
    }
}
