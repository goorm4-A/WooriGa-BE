package com.example.server.dto.familyDiary;

import com.example.server.domain.entity.Comment;
import com.example.server.domain.entity.FamilyDiary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CommentDto {

    private String content;
    private LocalDateTime createdAt;
    private String username;
    private Long familyMemberId; //댓글 작성자
    private Long familyDiaryId; //댓글의 원 게시글


    public static List<CommentDto> toDto(List<Comment> list) {
        return list.stream()
                .map(comment-> CommentDto.builder()
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .username(comment.getUsername())
                        .familyMemberId(comment.getFamilyMember().getId())
                        .familyDiaryId(comment.getFamilyDiary().getId())
                        .build()
                )
                .collect(Collectors.toList());

    }


}
