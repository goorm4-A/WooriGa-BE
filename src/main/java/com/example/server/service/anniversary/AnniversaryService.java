package com.example.server.service.anniversary;


import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.AnniversaryType;
import com.example.server.dto.anniversary.AnniversaryRequest;
import com.example.server.dto.anniversary.AnniversaryResponse;
import com.example.server.dto.anniversary.AnniversaryResponseList;
import com.example.server.dto.anniversary.AnniversaryScrollResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.anniversary.FamilyAnniversaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnniversaryService {

    private final UserRepository userRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyAnniversaryRepository familyAnniversaryRepository;



    /// 가족 기념일 등록
    public AnniversaryResponse createSchedule(AnniversaryRequest dto,User user){

        FamilyMember member=familyMemberRepository.findByUserIdAndFamilyId(user.getId(), dto.getFamilyId())
                .orElseThrow(()->new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        Family family=member.getFamily();

        //요청한 사람의 기념일 저장
        FamilyAnniversary anniversary=dto.toEntity(dto,user,member,family);
        familyAnniversaryRepository.save(anniversary);
        System.out.println("✅요청한 사람의 기념일 저장");

        //같은 family내의 다른 user들의 달력에도 해당 스케줄 저장
        Long familyId=dto.getFamilyId();
        List<FamilyMember> members=familyMemberRepository.findByFamilyId(familyId);

        //본인 제외한 다른 구성원에게도 동일한 기념일 추가
        members.stream()
                .filter(m->!m.getId().equals(member.getId()))
                .forEach(m->{
                    AnniversaryType typeEnum = AnniversaryType.fromDisplayName(dto.getType()); // 직접 파싱
                    FamilyAnniversary shared=FamilyAnniversary.builder()
                            .title(dto.getTitle())
                            .description(dto.getDescription())
                            .anniversaryType(typeEnum)
                            .date(dto.getDate())
                            .location(dto.getLocation())
                            .familyMember(m)
                            .family(m.getFamily())
                            .user(m.getUser())
                            .build();
                    familyAnniversaryRepository.save(shared);
                });
        return AnniversaryResponse.toDto(anniversary);

    }


    /// 가족 기념일 캘린더에서 보기 ///무한 스크롤 도입X
    public List<AnniversaryResponseList> getMonthSchedule(User user,int year,int month){

        List<FamilyAnniversary> anniversaries=user.getFamilyAnniversaries();

        //dto로 변환
        List<AnniversaryResponseList> filtered=anniversaries.stream()
                .filter(m->m.getDate().getYear()==year && m.getDate().getMonthValue()==month)
                .map(AnniversaryResponseList::toDto)
                .toList();

        return filtered;
    }

    /// 이번 달 기념일 한눈에 보기 ///D-DAY ///무한 스크롤 도입 O
//    public AnniversaryScrollResponse getAnniversaryDday(User user,Long lastAnniversaryId,Integer size){
//
//    }


    /// 가족 기념일 상세 보기
    public AnniversaryResponse getAnniversaryDetail(Long anniversaryId){
        FamilyAnniversary anniversary = familyAnniversaryRepository.findById(anniversaryId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ANNIVERSARY_NOT_FOUND));

        return AnniversaryResponse.toDto(anniversary);
    }

    /// 가족 기념일 전체 보기
    public AnniversaryScrollResponse getAllAnniversary(User user,Long lastAnniversaryId,Pageable pageable) {
        Long userId=user.getId();

        AnniversaryType type=null;
        List<FamilyAnniversary> anniversaryList=familyAnniversaryRepository.findByAnniversaryTypeWithCursor(type, userId,lastAnniversaryId,pageable);
        List<AnniversaryResponseList> dtoList=AnniversaryResponseList.toDtoList(anniversaryList);

        boolean hasNext=false;
        if(dtoList.size()>pageable.getPageSize()){
            hasNext=true;
            dtoList.remove(pageable.getPageSize()); //마지막 항목 제거
        }
        return new AnniversaryScrollResponse(dtoList,hasNext);
    }

     ///기념일 타입으로 검색
    public AnniversaryScrollResponse searchAnniversary(User user, String typeName, Long lastAnniversaryId, Pageable pageable) {
        Long userId=user.getId();
        AnniversaryType type = AnniversaryType.fromDisplayName(typeName);

        List<FamilyAnniversary> anniversaryList=familyAnniversaryRepository.findByAnniversaryTypeWithCursor(type, userId,lastAnniversaryId,pageable);
        List<AnniversaryResponseList> dtoList=AnniversaryResponseList.toDtoList(anniversaryList);

        boolean hasNext=false;
        if(dtoList.size()>pageable.getPageSize()){
            hasNext=true;
            dtoList.remove(pageable.getPageSize()); //마지막 항목 제거
        }
        return new AnniversaryScrollResponse(dtoList,hasNext);

    }


}

