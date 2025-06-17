package com.example.server.dto.familyDiary;

import com.example.server.domain.entity.DiaryTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryTagDto {

    private Long diaryTagId; //Tag id가 아닌, DiaryTag id
    private String diaryTagName;

    public DiaryTagDto(DiaryTag diaryTag) {
        this.diaryTagId = diaryTag.getId();
        this.diaryTagName=diaryTag.getTag().getName();
    }
}
