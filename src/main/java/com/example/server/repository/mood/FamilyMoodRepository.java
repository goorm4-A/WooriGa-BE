package com.example.server.repository.mood;

import com.example.server.domain.entity.FamilyMood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMoodRepository extends JpaRepository<FamilyMood, Long> {
    List<FamilyMood> findAllByFamilyMember_Family_Id(Long familyId);
}
