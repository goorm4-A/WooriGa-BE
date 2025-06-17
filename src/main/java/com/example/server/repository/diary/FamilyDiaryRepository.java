package com.example.server.repository.diary;

import com.example.server.domain.entity.FamilyDiary;
import com.example.server.repository.diary.query.FamilyDiaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyDiaryRepository extends JpaRepository<FamilyDiary, Long>, FamilyDiaryQueryRepository {

    List<FamilyDiary> findByFamilyId(Long familyId);
    List<FamilyDiary> findByFamilyIdAndTitleContaining(Long familyId, String title);
}
