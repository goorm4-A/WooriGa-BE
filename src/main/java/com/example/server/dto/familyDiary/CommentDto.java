package com.example.server.dto.familyDiary;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private String content;
    private LocalDateTime createdAt;
    private Long familyMemberId; //댓글 작성자
    private Long familyDiaryId; //댓글의 원 게시글

}
