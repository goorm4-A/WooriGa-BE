package com.example.server.dto.familyEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FamilyEventUpdateRequest {
    private String title;
    private LocalDate date;
    private String location;
    private String latitude;
    private String longitude;
}
