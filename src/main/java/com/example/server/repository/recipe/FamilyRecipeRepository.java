package com.example.server.repository.recipe;

import com.example.server.domain.entity.FamilyRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRecipeRepository extends JpaRepository<FamilyRecipe, Long> {
}
