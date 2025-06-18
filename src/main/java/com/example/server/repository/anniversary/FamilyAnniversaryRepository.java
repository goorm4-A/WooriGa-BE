package com.example.server.repository.anniversary;

import com.example.server.domain.entity.FamilyAnniversary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyAnniversaryRepository extends JpaRepository<FamilyAnniversary, Long> {
}
