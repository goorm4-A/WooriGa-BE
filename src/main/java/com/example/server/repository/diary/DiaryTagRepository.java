package com.example.server.repository.diary;

import com.example.server.domain.entity.DiaryTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryTagRepository extends JpaRepository<DiaryTag, Long> {

        List<DiaryTag> findAllByFamilyDiaryId(Long diaryId);
}
