package com.example.server.repository;

import com.example.server.domain.entity.FamilyDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyDiaryRepository extends JpaRepository<FamilyDiary, Long> {
}
