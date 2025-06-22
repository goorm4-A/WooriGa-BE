package com.example.server.dto.familyEvent;

import com.example.server.domain.entity.FamilyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyEventDetailDto {
    private String familyName;
    private String title;
    private LocalDate date;
    private String location;

    public static FamilyEventDetailDto fromEntity(FamilyEvent event) {
        return FamilyEventDetailDto.builder()
                .familyName(event.getFamilyMember().getFamily().getName())
                .title(event.getTitle())
                .date(event.getTimeline())
                .location(event.getLocation())
                .build();
    }
}
