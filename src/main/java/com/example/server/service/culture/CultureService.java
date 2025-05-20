package com.example.server.service.culture;

import com.example.server.converter.FamilyConverter;
import com.example.server.domian.entity.Family;
import com.example.server.domian.entity.FamilyMember;
import com.example.server.domian.entity.FamilyMotto;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.code.exception.handler.FamilyMottoHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyMottoRepository;
import com.example.server.repository.FamilyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CultureService {
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMottoRepository familyMottoRepository;
    @Transactional
    public FamilyMotto createMotto(CultureRequestDTO.MottoRequestDTO mottoRequestDTO, Long userId) {
        /*
        * TODO
        *  유저 사용자 조회 = 가족 구성원 조회
        *  가족 구성원 조회
        * */
        Family family = familyRepository.findByName(mottoRequestDTO.getFamilyName())
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        // TODO : 유저가 해당 가족 구성원으로 포함되어 있는 지 확인
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        FamilyMotto familyMotto = FamilyConverter.toFamilyMotto(mottoRequestDTO, familyMember, family);
        return familyMottoRepository.save(familyMotto);
    }

    @Transactional
    public void deleteMotto(Long mottoId, Long userId) {

        FamilyMotto familyMotto = familyMottoRepository.findById(mottoId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        // 현재 유저가 작성한 좌우명인지 확인
        if (!familyMotto.getFamilyMember().getUser().getId().equals(userId)) {
            throw new CustomException(ErrorStatus.COMMON_UNAUTHORIZED);
        }
        familyMottoRepository.delete(familyMotto);
    }
}
