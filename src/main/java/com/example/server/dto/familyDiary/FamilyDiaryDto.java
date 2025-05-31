package com.example.server.dto.familyDiary;

import com.example.server.domain.entity.FamilyDiary;
import com.example.server.domain.entity.DiaryParticipant;
import com.example.server.domain.entity.DiaryTag;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.domain.enums.ContentType;
import com.example.server.domain.enums.FromType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDiaryDto {
    private Long familyId;
    private Long userId;

//    private List<MultipartFile> image;
    private String title;
//    private List<Long> diaryTags; //객체 대신 문자열로 받기
    private List<Long> diaryParticipants; //participant ID만받기
    private String location;
    private String description;
    private FromType contentType;



    public static FamilyDiary fromDto(FamilyDiaryDto dto) {
        return new FamilyDiary(
                dto.getTitle(),
                dto.getLocation(),
                dto.getDescription(),
                dto.getContentType()
        );
    }


}
