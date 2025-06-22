package com.example.server.dto.familyEvent;

import com.example.server.domain.entity.FamilyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyEventResponse {
    private Long eventId;
    private String familyName;
    private String title;
    private LocalDate date;
    private String location;
    private String latitude;
    private String longitude;
}
