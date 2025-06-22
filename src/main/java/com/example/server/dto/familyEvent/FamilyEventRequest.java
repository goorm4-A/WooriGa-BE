package com.example.server.dto.familyEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class FamilyEventRequest {
    private String familyName;
    private String title;
    private LocalDate date;
    private String location;
    private Double latitude;
    private Double longitude;
}
