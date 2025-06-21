package com.example.server.repository;

import com.example.server.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family,Long> {
    Boolean existsByInviteCode(Integer inviteCode);
    Optional<Family> findByName(String name);
}
