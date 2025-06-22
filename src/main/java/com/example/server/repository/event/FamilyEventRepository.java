package com.example.server.repository.event;

import com.example.server.domain.entity.FamilyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyEventRepository extends JpaRepository<FamilyEvent, Long> {
    List<FamilyEvent> findAllByUser_IdOrderByTimelineDesc(Long userId);

    List<FamilyEvent> findAllByFamilyMember_Family_IdOrderByTimelineDesc(Long familyId);
}
