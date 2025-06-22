package com.example.server.repository.event;

import com.example.server.domain.entity.FamilyEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyEventRepository extends JpaRepository<FamilyEvent, Long> {
}
