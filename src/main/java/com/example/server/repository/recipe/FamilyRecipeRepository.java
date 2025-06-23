package com.example.server.repository.recipe;

import com.example.server.domain.entity.FamilyRecipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FamilyRecipeRepository extends JpaRepository<FamilyRecipe, Long> {

    @Query("""
        SELECT fr
          FROM FamilyRecipe fr
         WHERE fr.familyMember.family.id = :familyId
           AND (:lastId IS NULL OR fr.id < :lastId)
         ORDER BY fr.id DESC
        """)
    List<FamilyRecipe> findByFamilyId(
            @Param("familyId") Long familyId,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    @Query("""
    SELECT ci.imageUrl
      FROM FamilyRecipe fr
      JOIN fr.coverImages ci
      JOIN fr.familyMember fm
     WHERE fm.user.id = :userId
       AND DATE(fr.createdAt) = :today
       AND ci.imageUrl IS NOT NULL AND ci.imageUrl <> ''
    """)
    List<String> findTodayRecipeImages(@Param("userId") Long userId, @Param("today") LocalDate today);
}
