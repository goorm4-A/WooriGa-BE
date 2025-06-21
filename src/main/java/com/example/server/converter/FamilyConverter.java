package com.example.server.converter;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.FamilyMotto;
import com.example.server.domain.entity.User;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.dto.member.FamilyGroupDetailResponse;
import com.example.server.dto.member.FamilyGroupResponse;
import com.example.server.dto.member.FamilyResponse;
import com.example.server.dto.member.FamilyMemberResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

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

    // Family > FamilyResponse : 가족 그룹 생성 시
    public static FamilyResponse toFamilyGroupResponse(Family family) {
        return FamilyResponse.builder()
                .familyGroupId(family.getId())
                .familyName(family.getName())
                .inviteCode(family.getInviteCode())
                .familyImage(family.getImage())
                .build();
    }

    // // List<Family> > List<FamilyGroupResponse> : 가족 그룹 조회 시
    public static List<FamilyGroupResponse> toFamilyGroupListResponse(List<Family> families) {
        return families.stream()
                .map(family -> FamilyGroupResponse.builder()
                        .familyGroup(toFamilyGroupResponse(family))
                        .familyMembers(family.getFamilyMembers().stream()
                                .limit(3)
                                .map(FamilyConverter::toFamilyMemberResponse)
                                .collect(Collectors.toList()))
                        .totalCnt(family.getFamilyMembers().size())
                        .build())
                .toList();
    }

    // FamilyMember > FamilyMemberResponse : 가족 그룹 조회 후 각 familyMember 변환 시
    public static FamilyMemberResponse toFamilyMemberResponse(FamilyMember familyMember) {
        User user = familyMember.getUser(); // 초반 user가 null인 경우 존재

        return FamilyMemberResponse.builder()
                .familyMemberId(familyMember.getId())
                .familyMemberName(user == null? familyMember.getMemberName() : user.getName())
                .birthDate(user == null? familyMember.getMemberBirthDate() : user.getBirthDate())
                .familyMemberImage(familyMember.getImage())
                .relation(familyMember.getRelation())
                .isUserAdded(familyMember.getIsUserAdded())
                .build();
    }

    // Family > FamilyGroupDetailResponse : 가족 그룹 상세 조회 시
    public static FamilyGroupDetailResponse toFamilyGroupDetailResponse(List<FamilyMember> familyMembers) {
        return FamilyGroupDetailResponse.builder()
                .familyMembers(familyMembers.stream()
                        .map(FamilyConverter::toFamilyMemberResponse)
                        .collect(Collectors.toList())
                ).build();
    }
}
