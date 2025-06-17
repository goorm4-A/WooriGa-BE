package com.example.server.dto.familyDiary;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FamilyDiaryScrollResponse {

    private final List<FamilyDiaryListDto> contents;
    private final boolean hasNext;
}
