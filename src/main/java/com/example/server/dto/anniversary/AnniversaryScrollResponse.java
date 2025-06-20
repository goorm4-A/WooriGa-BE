package com.example.server.dto.anniversary;


import com.example.server.dto.familyDiary.FamilyDiaryListDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AnniversaryScrollResponse {

    private final List<AnniversaryResponseList> contents;
    private final boolean hasNext;
}
