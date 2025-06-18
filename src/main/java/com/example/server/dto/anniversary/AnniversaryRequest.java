package com.example.server.dto.anniversary;

import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.AnniversaryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnniversaryRequest {

    private Long userId;
    private Long familyMemberId; //? //가족 구성원 중 한명이 스케줄 등록-> 다른 가족 구성원들의 달력에도 스케줄이 나타나도록
    private String title;
    private LocalDate date;
    private AnniversaryType type; //태그
    private String location;
    private String description;

    public FamilyAnniversary toEntity(AnniversaryRequest dto, User user, FamilyMember member){
        return FamilyAnniversary.builder()
                .title(dto.getTitle())
                .anniversaryType(dto.getType())
                .date(dto.getDate())
                .location(dto.getLocation())
                .user(user)
                .familyMember(member)
                .build();

    }

}
