package com.example.server.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FamilyMemberDetailDTO {
    private String memberName;
    private String memberImage;
    private String relation;
    private LocalDate memberBirthDate;
}
