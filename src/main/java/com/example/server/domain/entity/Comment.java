package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt; //작성시간

    @ManyToOne
    private FamilyMember author;
    
    @ManyToOne
    private FamilyDiary familyDiary;
    
    //부모 댓글?//대댓글 처리 방식

    
}
