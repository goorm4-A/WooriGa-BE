package com.example.server.repository.mood;

import com.example.server.domain.entity.MoodTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodTagRepository extends JpaRepository<MoodTag, Long> {
    List<MoodTag> findAllByFamilyMoodId(Long moodId);
}
