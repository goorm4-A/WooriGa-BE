package com.example.server.repository.anniversary.query;

import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.User;

import com.example.server.domain.enums.AnniversaryType;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FamilyAnniversaryQueryRepository {
//
//    List<FamilyAnniversary> findByUserWithCursor(Long userId, Long lastAnniversaryId, Pageable pageable);
    List<FamilyAnniversary> findByAnniversaryTypeWithCursor(AnniversaryType anniversaryType, Long lastAnniversaryType,Long userId,Pageable pageable);
}
