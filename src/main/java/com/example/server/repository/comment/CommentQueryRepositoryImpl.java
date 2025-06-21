package com.example.server.repository.comment;

import com.example.server.domain.entity.Comment;
import com.example.server.domain.entity.QComment;
import com.example.server.domain.entity.QFamilyDiary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;
    public CommentQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Comment> findByDiaryIdWithCursor(Long familyDiaryId, Long lastCommentId, Pageable pageable) {
        QComment comment = QComment.comment;
        QFamilyDiary familyDiary = QFamilyDiary.familyDiary;

        List<Comment> results=queryFactory
                .selectFrom(comment)
                .where(
                        comment.familyDiary.id.eq(familyDiaryId),
                        gtCommentId(lastCommentId,comment)
                )
                .orderBy(comment.id.asc()) //예전 댓글부터 보여주기
                .limit(pageable.getPageSize()+1)
                .fetch();

        return results;
    }

    @Override
    public List<Comment> findByCommentIdWithCursor(Long commentId, Long lastReCommentId, Pageable pageable) {
        QComment comment = QComment.comment;
        List<Comment> results=queryFactory
                .selectFrom(comment)
                .where(
                        comment.id.eq(commentId),
                        gtCommentId(lastReCommentId,comment)
                )
                .orderBy(comment.id.asc()) //예전 댓글부터 보여주기
                .limit(pageable.getPageSize()+1)
                .fetch();
        return results;
    }

    @Override
    public List<Comment> findByRecipeIdWithCursor(Long recipeId, Long lastCommentId, Pageable pageable) {
        QComment comment = QComment.comment;
        List<Comment> results = queryFactory
                .selectFrom(comment)
                .where(
                        comment.recipe.id.eq(recipeId),
                        gtCommentId(lastCommentId, comment)
                )
                .orderBy(comment.id.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return results;
    }

    // 커서 기준으로 이후 댓글만 조회 (오래된 댓글 순으로 보기 위함)
    private BooleanExpression gtCommentId(Long lastCommentId,QComment comment) {
        return lastCommentId!=null?comment.id.gt(lastCommentId):null;
    }
}
