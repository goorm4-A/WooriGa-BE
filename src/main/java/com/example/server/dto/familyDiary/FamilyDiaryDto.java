package com.example.server.dto.familyDiary;

import com.example.server.domian.entity.DiaryParticipant;
import com.example.server.domian.entity.DiaryTag;
import com.example.server.domian.entity.Family;
import com.example.server.domian.entity.FamilyDiary;
import com.example.server.domian.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Text;

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
    private ContentType contentType;



    public static FamilyDiary fromDto(FamilyDiaryDto dto) {
        return new FamilyDiary(
                dto.getTitle(),
                dto.getLocation(),
                dto.getDescription(),
                dto.getContentType()
        );
    }


}
