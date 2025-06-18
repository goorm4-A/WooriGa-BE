package com.example.server.service.anniversary;


import com.example.server.domain.entity.FamilyAnniversary;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.anniversary.AnniversaryRequest;
import com.example.server.dto.anniversary.AnniversaryResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.anniversary.FamilyAnniversaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnniversaryService {

    private final UserRepository userRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyAnniversaryRepository familyAnniversaryRepository;



    /// 가족 기념일 등록
    public AnniversaryResponse createSchedule(AnniversaryRequest dto){

        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(()->new CustomException(ErrorStatus.USER_NOT_FOUND));
        System.out.println("✅user:"+user.getName());

        FamilyMember member=familyMemberRepository
                .findById(dto.getFamilyMemberId())
                .orElseThrow(()->new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
        System.out.println("✅member:"+member.getId());

        //요청한 사람의 기념일 저장
        FamilyAnniversary anniversary=dto.toEntity(dto,user,member);
        familyAnniversaryRepository.save(anniversary);
        System.out.println("✅요청한 사람의 기념일 저장");

        //같은 family내의 다른 user들의 달력에도 해당 스케줄 저장
        Long familyId=member.getFamily().getId();
        List<FamilyMember> members=familyMemberRepository.findByFamilyId(familyId);

        //본인 제외한 다른 구성원에게도 동일한 기념일 추가
        members.stream()
                .filter(m->!m.getId().equals(member.getId()))
                .forEach(m->{
                    FamilyAnniversary shared=FamilyAnniversary.builder()
                            .title(dto.getTitle())
                            .description(dto.getDescription())
                            .anniversaryType(dto.getType())
                            .date(dto.getDate())
                            .location(dto.getLocation())
                            .familyMember(m)
                            .user(m.getUser())
                            .build();
                    familyAnniversaryRepository.save(shared);
                });
        return AnniversaryResponse.toDto(anniversary);

    }

}

