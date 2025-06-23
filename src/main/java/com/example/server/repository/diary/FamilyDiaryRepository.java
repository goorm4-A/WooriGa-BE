package com.example.server.repository.diary;

import com.example.server.domain.entity.FamilyDiary;
import com.example.server.repository.diary.query.FamilyDiaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FamilyDiaryRepository extends JpaRepository<FamilyDiary, Long>, FamilyDiaryQueryRepository {

    List<FamilyDiary> findByFamilyId(Long familyId);
    List<FamilyDiary> findByFamilyIdAndTitleContaining(Long familyId, String title);

    @Query("SELECT di.imgUrl FROM FamilyDiary fd " +
            "JOIN fd.images di " +
            "JOIN fd.familyMember fm " +
            "WHERE fm.user.id = :userId " +
            "AND DATE(fd.writtenDate) = :today " +
            "AND di.imgUrl IS NOT NULL AND di.imgUrl <> ''")
    List<String> findTodayDiaryImageUrls(@Param("userId") Long userId, @Param("today") LocalDate today);

}
