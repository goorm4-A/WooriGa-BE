package com.example.server.repository.comment;

import com.example.server.domain.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentQueryRepository {

    List<Comment> findByDiaryIdWithCursor(Long familyDiaryId, Long commentId, Pageable pageable);
    List<Comment> findByCommentIdWithCursor(Long commentId, Long lastReCommentId,Pageable pageable);
}
