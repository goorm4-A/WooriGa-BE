package com.example.server.repository;

import com.example.server.domian.entity.FamilyMotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMottoRepository extends JpaRepository<FamilyMotto, Long> {
}
