package com.example.server.repository.anniversary;

import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.AnniversaryType;
import com.example.server.repository.anniversary.query.FamilyAnniversaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyAnniversaryRepository extends JpaRepository<FamilyAnniversary, Long>, FamilyAnniversaryQueryRepository {
    List<FamilyAnniversary> findByAnniversaryType(AnniversaryType type);
    List<FamilyAnniversary> findByUser(User user);

}
