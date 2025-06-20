package com.example.server.dto.anniversary;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.AnniversaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AnniversaryResponse {

    private Long id;
    private Long familyId;
    private String title;
    private LocalDate date;
    private String anniversaryType; //태그
    private String location;
    private String description;

    public static AnniversaryResponse toDto(FamilyAnniversary anniversary) {
        return AnniversaryResponse.builder()
                .id(anniversary.getId())
                .title(anniversary.getTitle())
                .anniversaryType(anniversary.getAnniversaryType().getDisplayName())
                .date(anniversary.getDate())
                .description(anniversary.getDescription())
                .location(anniversary.getLocation())
                .familyId(anniversary.getFamilyMember().getFamily().getId())
                .build();

    }

}
