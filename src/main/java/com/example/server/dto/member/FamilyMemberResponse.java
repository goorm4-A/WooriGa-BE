package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FamilyMemberResponse {
    private Long familyMemberId;
    private String familyMemberName;
    private String familyMemberImage;
    private String relation;
    private LocalDate birthDate;
    private Boolean isUserAdded;
}
