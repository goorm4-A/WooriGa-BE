package com.example.server.dto;

import com.example.server.domain.entity.Comment;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.domain.entity.FamilyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest {

//    private Long id;
    private String content;
    private LocalDateTime createdAt=LocalDateTime.now();
//    private Long familyMemberId;


    public Comment toEntity(FamilyMember member, FamilyDiary diary,String username){ //생성자를 사용해 객체 생성
        return Comment.builder()
                .content(content)
                .createdAt(createdAt)
                .username(username)
                .familyMember(member)
                .familyDiary(diary)
                .build();
    }
}
