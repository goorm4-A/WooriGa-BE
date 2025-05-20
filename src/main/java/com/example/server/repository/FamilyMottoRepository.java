package com.example.server.repository;

import com.example.server.domian.entity.FamilyMotto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMottoRepository extends JpaRepository<FamilyMotto, Long> {
    //특정 가족의 좌우명 조회
    @Query("SELECT fm FROM FamilyMotto fm WHERE fm.family.id = :familyId AND (:lastId IS NULL OR fm.id < :lastId) ORDER BY fm.id DESC")
    List<FamilyMotto> findByFamilyIdAndIdLessThan(
            @Param("familyId") Long familyId,
            @Param("lastId") Long lastId,
            Pageable pageable);

    //사용자의 모든 가족 좌우명 조회
    @Query("SELECT fm FROM FamilyMotto fm " +
            "JOIN fm.familyMember fm2 " +
            "WHERE fm2.user.id = :userId AND (:lastId IS NULL OR fm.id < :lastId) " +
            "ORDER BY fm.id DESC")
    List<FamilyMotto> findByUserIdAndIdLessThan(
            @Param("userId") Long userId,
            @Param("lastId") Long lastId,
            Pageable pageable);
}
