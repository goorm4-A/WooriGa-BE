package com.example.server.domain.entity;

import com.example.server.dto.AddCommentRequest;
import com.example.server.dto.familyDiary.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable=false)
    private String content;

//    private LocalDateTime createdAt; //작성시간

    private String username; //작성자 이름 //✏️맞는지 확인

    @ManyToOne
    @JoinColumn(name="family_member_id")
    private FamilyMember familyMember;
    
    @ManyToOne
    @JoinColumn(name="family_diary_id")
    private FamilyDiary familyDiary;

    @ManyToOne
    @JoinColumn(name="family_recipe_id")
    private FamilyRecipe recipe;
    
    //부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_comment_id")
    Comment parentComment;

    @Builder.Default
    @OneToMany(mappedBy="parentComment", orphanRemoval=true) //원 댓글 삭제 -> 자식 댓글 모두 삭제
    private List<Comment> childComments=new ArrayList<>();

    //댓글 수정
    public void update(String content){
        this.content = content;
    }



//    public CommentDto toDto(){
//        return new CommentDto(
//                this.content,
//                this.createdAt,
//                this.author.getId(),
//                this.familyDiary.getId()
//        );
//    }

    
}
