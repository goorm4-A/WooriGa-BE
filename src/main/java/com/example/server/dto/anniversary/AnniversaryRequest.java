package com.example.server.dto.anniversary;

import com.example.server.domain.entity.Family;
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

    private Long familyId;
    private String title;
    private LocalDate date;
    private String type; //태그
    private String location;
    private String description;

    public FamilyAnniversary toEntity(AnniversaryRequest dto, User user, FamilyMember member, Family family){
        AnniversaryType typeEnum = AnniversaryType.fromDisplayName(type); // 직접 파싱
        return FamilyAnniversary.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .family(family)
                .anniversaryType(typeEnum)
                .date(dto.getDate())
                .location(dto.getLocation())
                .user(user)
                .familyMember(member)
                .build();

    }

}
