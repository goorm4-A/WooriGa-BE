package com.example.server.service;

import com.example.server.converter.RecipeConverter;
import com.example.server.domain.entity.*;
import com.example.server.dto.AddCommentRequest;
import com.example.server.dto.familyDiary.CommentDto;
import com.example.server.dto.familyDiary.CommentResponse;
import com.example.server.dto.familyRecipe.RecipeRequestDTO;
import com.example.server.dto.familyRecipe.RecipeResponseDTO;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.code.exception.handler.FamilyRecipeHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.comment.CommentRepository;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.diary.FamilyDiaryRepository;
import com.example.server.repository.recipe.FamilyRecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyDiaryRepository familyDiaryRepository;
    private final FamilyRepository familyRepository;
    private final FamilyRecipeRepository familyRecipeRepository;

    //댓글 추가 메서드
    public CommentDto save(AddCommentRequest request, Long diaryId, User user) {

        FamilyDiary diary = familyDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));

        String username=user.getName();

        //Familymember 찾기
        Long familyId=diary.getFamily().getId();
        FamilyMember member=familyMemberRepository.findByUserIdAndFamilyId(user.getId(), familyId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        Comment comment = request.toEntity(member, diary,username);
        commentRepository.save(comment);

        return CommentDto.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .username(username)
                .familyMemberId(member.getId())
                .familyDiaryId(diary.getId())
                .build();
    }

    @Transactional
    public RecipeResponseDTO.recipeCommentDto save(Long familyId, Long recipeId, RecipeRequestDTO.addRecipeCommentRequest request, User user) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));

        FamilyMember member = familyMemberRepository.findByUserIdAndFamily(user.getId(), family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        FamilyRecipe recipe = familyRecipeRepository.findById(recipeId)
                .orElseThrow(() -> new FamilyRecipeHandler(ErrorStatus.FAMILY_RECIPE_NOT_FOUND));

        if (!recipe.getFamilyMember().getFamily().getId().equals(familyId)) {
            throw new FamilyRecipeHandler(ErrorStatus.FAMILY_RECIPE_NOT_FOUND);
        }

        Comment comment = RecipeConverter.toComment(member, recipe, user.getName(), request);
        commentRepository.save(comment);

        return RecipeConverter.toRecipeCommentDto(comment);
    }

    //대댓글 추가 메서드
    public CommentDto saveRecomment(AddCommentRequest request, Long diaryId,Long commentId,User user) {

        String username=user.getName();
//        Long diaryId=request.getFamilyDiaryId();

        FamilyDiary diary = familyDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));

        //Familymember 찾기
        Long familyId=diary.getFamily().getId();
        FamilyMember member=familyMemberRepository.findByUserIdAndFamilyId(user.getId(), familyId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomException(ErrorStatus.COMMENT_NOT_FOUND));
//        FamilyDiary diary=comment.getFamilyDiary();

        Comment childComment=request.toEntity(member,diary,username);
        childComment.setParentComment(comment); //부모 댓글 추가
        commentRepository.save(childComment);

        return CommentDto.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .username(username)
                .familyMemberId(member.getId())
                .familyDiaryId(diary.getId())
                .build();

    }

    //댓글 삭제 메서드
    public void deleteComment(Long commentId){
        Comment comment=commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomException(ErrorStatus.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }

    //댓글 조회 메서드 (대댓글 제외)
    public CommentResponse showComments(Long familyDiaryId, Long commentId, Pageable pageable){
        List<Comment> commentList=commentRepository.findByDiaryIdWithCursor(familyDiaryId,commentId,pageable);
        List<CommentDto> dtoList=CommentDto.toDto(commentList);

        //무한 스크롤 로직
        boolean hasNext=false;
        if(dtoList.size()>pageable.getPageSize()){
            hasNext=true;
            dtoList.remove(pageable.getPageSize());
        }
        return new CommentResponse(dtoList,hasNext);
    }

    //대댓글 조회 메서드
    public CommentResponse showReComments(Long commentId, Long reCommentId, Pageable pageable){
        List<Comment> commentList=commentRepository.findByCommentIdWithCursor(commentId,reCommentId,pageable);
        List<CommentDto> dtoList=CommentDto.toDto(commentList);

        //무한 스크롤 로직
        boolean hasNext=false;
        if(dtoList.size()>pageable.getPageSize()){
            hasNext=true;
            dtoList.remove(pageable.getPageSize());
        }
        return new CommentResponse(dtoList,hasNext);

    }
}
