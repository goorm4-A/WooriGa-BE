package com.example.server.dto.familyDiary;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private final List<CommentDto> comments;
    private final boolean hasNext;
}
