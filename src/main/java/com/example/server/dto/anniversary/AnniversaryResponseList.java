package com.example.server.dto.anniversary;

import com.example.server.domain.entity.Comment;
import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.dto.familyDiary.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class AnniversaryResponseList {

    private Long anniversaryId;
    private Long familyId;
    private String title;
    private String location;
    private LocalDate date;

    public static AnniversaryResponseList toDto(FamilyAnniversary anniversary){
        return AnniversaryResponseList.builder()
                .anniversaryId(anniversary.getId())
                .familyId(anniversary.getFamily().getId())
                .title(anniversary.getTitle())
                .location(anniversary.getLocation())
                .date(anniversary.getDate())
                .build();
    }

    public static List<AnniversaryResponseList> toDtoList(List<FamilyAnniversary> list) {
        return list.stream()
                .map(ani-> AnniversaryResponseList.builder()
                        .anniversaryId(ani.getId())
                        .familyId(ani.getFamily().getId())
                        .title(ani.getTitle())
                        .location(ani.getLocation())
                        .date(ani.getDate())
                        .build()
                )
                .collect(Collectors.toList());

    }
}
