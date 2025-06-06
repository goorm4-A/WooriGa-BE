package com.example.server.dto.familyDiary;

import com.example.server.domain.enums.FromType;
import com.example.server.domain.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDiaryResponseDto {

    private Long diaryId;
    private String title;
    private String location;
    private String description;
    private FromType contentType;
    private List<Long> participantIds;


//    private List<Long> diaryTags; //추가 예정
}
