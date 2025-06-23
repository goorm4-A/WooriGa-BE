package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FamilyMemberDetailResponse {
    private Long familyMemberId;
    private String familyMemberName;
    private String familyMemberImage;
    private String relation;
    private LocalDate birthDate;
    private Double x;
    private Double y;
    private Boolean isUserAdded;
}
