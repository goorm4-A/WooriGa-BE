package com.example.server.repository.diary.query;


import com.example.server.domain.entity.FamilyDiary;
import com.example.server.domain.entity.QDiaryTag;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import com.example.server.domain.entity.QFamilyDiary;


import java.util.List;

public class FamilyDiaryQueryRepositoryImpl implements FamilyDiaryQueryRepository{


    private final JPAQueryFactory queryFactory;

    public FamilyDiaryQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<FamilyDiary> findByFamilyIdWithCursor(Long familyId, Long lastDiaryId, Pageable pageable) {
        QFamilyDiary familyDiary=QFamilyDiary.familyDiary; //FamilyDiary 엔티티의 각 필드를 객체로 표현(diary.id, diary.title)

        List<FamilyDiary> results=queryFactory
                .selectFrom(familyDiary)
                .where(
                        familyDiary.family.id.eq(familyId),
                        ltLastDiaryId(lastDiaryId,familyDiary) //lastDiaryId를 통해 그 이후의 데이터만 검색하도록 설정->성능 저하 방지
                )
                .orderBy(familyDiary.id.desc()) //초기 요청 시, lastDiaryId 없음 -> null 반환 -> orderBy를 통해 정렬된 데이터 중 가장 최근의 데이터 반환하도록 함
                .limit(pageable.getPageSize()+1) //프론트가 요청한 사이즈보다 1크게 설정 -> 다음 페이지 존재 여부에 활용
                .fetch();
        return results;
    }

    @Override
    public List<FamilyDiary> searchByTitleWithCursor(Long familyId, String keyword, Long lastDiaryId, Pageable pageable) {
        QFamilyDiary diary=QFamilyDiary.familyDiary;

        return queryFactory
                .selectFrom(diary)
                .where(
                        diary.family.id.eq(familyId),
//                        keywordContains(keyword,diary,tag),
                        ltLastDiaryId(lastDiaryId,diary)
                )
                .orderBy(diary.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();


    }

    //lastDiaryId 보다 ID가 작은 데이터만 조회 (동적 조건)
    private BooleanExpression ltLastDiaryId(Long lastDiaryId,QFamilyDiary familyDiary) {
        return lastDiaryId!=null?familyDiary.id.lt(lastDiaryId):null;
    }

//    private void keywordContains(String keyword, QFamilyDiary diary, QDiaryTag tag) {
//        if(keyword==null||keyword.isEmpty())
//    }
}
