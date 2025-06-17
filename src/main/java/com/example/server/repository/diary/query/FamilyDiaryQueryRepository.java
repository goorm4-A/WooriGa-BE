package com.example.server.repository.diary.query;

import com.example.server.domain.entity.FamilyDiary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FamilyDiaryQueryRepository {
    List<FamilyDiary> findByFamilyIdWithCursor(Long familyId, Long lastDiaryId, Pageable pageable);
}
