package com.example.server.dto.anniversary;

import com.example.server.domain.entity.FamilyAnniversary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AnniversaryResponseList {

    private Long id;
    private String title;
    private String location;
    private LocalDate date;

    public static AnniversaryResponseList toDto(FamilyAnniversary anniversary){
        return AnniversaryResponseList.builder()
                .id(anniversary.getId())
                .title(anniversary.getTitle())
                .location(anniversary.getLocation())
                .date(anniversary.getDate())
                .build();
    }
}
