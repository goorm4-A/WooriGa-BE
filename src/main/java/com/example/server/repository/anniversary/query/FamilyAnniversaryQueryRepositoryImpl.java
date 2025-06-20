package com.example.server.repository.anniversary.query;

import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.QFamilyAnniversary;
import com.example.server.domain.enums.AnniversaryType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


import org.springframework.data.domain.Pageable;
import java.util.List;

public class FamilyAnniversaryQueryRepositoryImpl implements FamilyAnniversaryQueryRepository {

    private final JPAQueryFactory queryFactory;
    public FamilyAnniversaryQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<FamilyAnniversary> findByAnniversaryTypeWithCursor(AnniversaryType anniversaryType,Long userId, Long lastAnniversaryId, Pageable pageable) {
        QFamilyAnniversary anniversary=QFamilyAnniversary.familyAnniversary;

        List<FamilyAnniversary> results=queryFactory
                .selectFrom(anniversary)
                .where(
                        anniversary.user.id.eq(userId),
                        searchWithTag(anniversaryType,anniversary),
                        gtAnniversaryId(lastAnniversaryId,anniversary) //커서 기반 필터 //lastAnniversaryId 보다 큰 항목만 가져옴
                )
                .orderBy(anniversary.id.desc()) //✏️정렬 방식 변경해야
                .limit(pageable.getPageSize()+1) //다음 페이지 유무 판별
                .fetch();

        return results;
    }



    private BooleanExpression gtAnniversaryId(Long lastAnniversaryId, QFamilyAnniversary anniversary) {
        //lastAnniversaryId가 null인 경우, 아무 조건없이 검색하도록 //nullㅣ 이면 조건 생략(where에서 무시됨)
        return lastAnniversaryId!=null?anniversary.id.gt(lastAnniversaryId):null;
    }

    //태그를 통해 검색
    private BooleanExpression searchWithTag(AnniversaryType anniversaryType,QFamilyAnniversary anniversary) {
        //tag가 있는 경우에만 tag를 통해 검색하도록
        return anniversaryType!=null?anniversary.anniversaryType.eq(anniversaryType):null;
    }
}
