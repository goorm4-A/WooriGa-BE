package com.example.server.repository;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    Optional<FamilyMember> findByUserIdAndFamily(Long userId, Family family);
}
