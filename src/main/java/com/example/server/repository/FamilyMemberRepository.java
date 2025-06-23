package com.example.server.repository;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Optional<FamilyMember> findByUserIdAndFamilyId(Long userId, Long familyId);

    Optional<FamilyMember> findByUserIdAndFamily(Long userId, Family family);

    List<FamilyMember> findByFamilyId(Long familyId);


    Optional<FamilyMember> findByUserId(Long userId);

    List<FamilyMember> findAllByUser(User user);

    @Query("""

            SELECT fm.image
      FROM FamilyMember fm
     WHERE fm.isUserAdded = true
       AND fm.family.id IN :familyIds
       AND DATE(fm.createdAt) = :today
       AND fm.image IS NOT NULL
       AND fm.image <> ''
    """)
    List<String> findTodayFamilyMemberImages(
            @Param("familyIds") List<Long> familyIds,
            @Param("today") LocalDate today
    );
}
