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
public class FamilyEventTimelineDto {
    private String title;
    private LocalDate date;

    public static FamilyEventTimelineDto fromEntity(FamilyEvent event) {
        return FamilyEventTimelineDto.builder()
                .title(event.getTitle())
                .date(event.getTimeline())
                .build();
    }
}