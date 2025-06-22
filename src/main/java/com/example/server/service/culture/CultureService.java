package com.example.server.service.culture;

import com.example.server.converter.CultureConverter;
import com.example.server.converter.FamilyConverter;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.FamilyMotto;
import com.example.server.domain.entity.User;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.dto.culture.CultureResponseDTO;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.code.exception.handler.FamilyHandler;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.code.exception.handler.FamilyMottoHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyMottoRepository;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CultureService {
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMottoRepository familyMottoRepository;
    private final UserRepository userRepository;
    private static final int PAGE_SIZE = 8; // 한 번에 조회할 데이터 수
    @Transactional
    public FamilyMotto createMotto(CultureRequestDTO.MottoRequestDTO mottoRequestDTO, Long userId, Long familyId) {
        /*
        * TODO
        *  유저 사용자 조회 = 가족 구성원 조회
        *  가족 구성원 조회
        * */
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyMotto familyMotto = FamilyConverter.toFamilyMotto(mottoRequestDTO, familyMember, family);
        return familyMottoRepository.save(familyMotto);
    }

    @Transactional
    public void deleteMotto(Long mottoId, Long userId) {

        FamilyMotto familyMotto = familyMottoRepository.findById(mottoId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, familyMotto.getFamily())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        familyMottoRepository.delete(familyMotto);
    }

    @Transactional(readOnly = true)
    public CultureResponseDTO.MottoListResponseDTO getMottoList(Long familyId, String cursor, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorStatus.COMMON_UNAUTHORIZED));

        Long lastId = null;
        if (cursor != null && !cursor.isEmpty()) {
            try {
                lastId = Long.parseLong(cursor);
            } catch (NumberFormatException e) {
                throw new CustomException(ErrorStatus.COMMON_BAD_REQUEST);
            }
        }

        List<FamilyMotto> familyMottos;
        Pageable pageable = PageRequest.of(0, PAGE_SIZE + 1);

        /*
        * TODO : RULETYPE이 없는 객체 조회
        */
        if (familyId == null) { // familyId가 없을 경우 사용자의 모든 가족 좌우명 조회
            familyMottos = familyMottoRepository.findMottosByUserIdAndRuleTypeIsNull(userId, lastId, pageable);
        } else {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
            familyMemberRepository.findByUserIdAndFamily(userId, family)
                    .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
            familyMottos = familyMottoRepository.findMottosByFamilyIdAndRuleTypeIsNull(familyId, lastId, pageable);
        }

        // 다음 페이지 존재 여부 확인
        boolean hasNext = familyMottos.size() > PAGE_SIZE;

        List<FamilyMotto> responseMottos = hasNext
                ? familyMottos.subList(0, PAGE_SIZE)
                : familyMottos;

        Long nextCursor = hasNext && !responseMottos.isEmpty()
                ? responseMottos.get(responseMottos.size() - 1).getId()
                : null;

        return CultureConverter.toMottoListResponseDTO(responseMottos, hasNext, nextCursor);
    }
    @Transactional
    public CultureResponseDTO.MottoResponseDTO updateMotto(Long mottoId, CultureRequestDTO.MottoRequestDTO requestDTO, Long userId) {
        FamilyMotto familyMotto = familyMottoRepository.findById(mottoId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, familyMotto.getFamily())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
        // 만약 가족 정보를 수정한 경우
        if (!familyMotto.getFamily().getName().equals(requestDTO.getFamilyName())) {
            Family family = familyRepository.findByName(requestDTO.getFamilyName()).orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
            FamilyMotto newFamilyMotto = createMotto(requestDTO, userId, family.getId());
            deleteMotto(mottoId, userId);
            return CultureConverter.toMottoResponseDTO(newFamilyMotto);
        } else {
            familyMotto.updateMotto(requestDTO.getMotto());
            return CultureConverter.toMottoResponseDTO(familyMotto);
        }
    }

    @Transactional
    public FamilyMotto createRule(CultureRequestDTO.@Valid RuleRequestDTO requestDTO, Long userId) {
        Family family = familyRepository.findByName(requestDTO.getFamilyName())
                .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
        // TODO : 유저가 해당 가족 구성원으로 포함되어 있는 지 확인
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, family)
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyMotto familyMotto = FamilyConverter.toFamilyRule(requestDTO, familyMember, family);
        return familyMottoRepository.save(familyMotto);
    }

    public CultureResponseDTO.RuleListResponseDTO getRuleList(Long familyId, String cursor, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorStatus.COMMON_UNAUTHORIZED));

        Long lastId = null;
        if (cursor != null && !cursor.isEmpty()) {
            try {
                lastId = Long.parseLong(cursor);
            } catch (NumberFormatException e) {
                throw new CustomException(ErrorStatus.COMMON_BAD_REQUEST);
            }
        }

        List<FamilyMotto> familyMottos;
        Pageable pageable = PageRequest.of(0, PAGE_SIZE + 1);
        /*
        * TODO : RuleType이 존재하는 객체만 조회
        * */
        if (familyId == null) { // familyId가 없을 경우 사용자의 모든 가족 좌우명 조회
            familyMottos = familyMottoRepository.findRulesByUserIdAndRuleTypeIsNotNull(userId, lastId, pageable);
        } else {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new FamilyHandler(ErrorStatus.FAMILY_NOT_FOUND));
            familyMemberRepository.findByUserIdAndFamily(userId, family)
                    .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
            familyMottos = familyMottoRepository.findRulesByFamilyIdAndRuleTypeIsNotNull(familyId, lastId, pageable);
        }

        // 다음 페이지 존재 여부 확인
        boolean hasNext = familyMottos.size() > PAGE_SIZE;

        List<FamilyMotto> responseRules = hasNext
                ? familyMottos.subList(0, PAGE_SIZE)
                : familyMottos;

        Long nextCursor = hasNext && !responseRules.isEmpty()
                ? responseRules.get(responseRules.size() - 1).getId()
                : null;

        return CultureConverter.toRuleListResponseDTO(responseRules, hasNext, nextCursor);
    }

    public CultureResponseDTO.RuleResponseDTO getRule(Long ruleId, Long userId) {
        FamilyMotto familyRule = familyMottoRepository.findById(ruleId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        return CultureConverter.toRuleResponseDTO(familyRule);
    }
    @Transactional
    public CultureResponseDTO.RuleResponseDTO updateRule(Long ruleId, CultureRequestDTO.@Valid RuleRequestDTO requestDTO, Long userId) {
        FamilyMotto familyRule = familyMottoRepository.findById(ruleId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, familyRule.getFamily())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
        // 만약 가족 정보를 수정한 경우
        if (!familyRule.getFamily().getName().equals(requestDTO.getFamilyName())) {
            FamilyMotto newFamilyRule = createRule(requestDTO, userId);
            deleteMotto(ruleId, userId);
            return CultureConverter.toRuleResponseDTO(newFamilyRule);
        } else {
            familyRule.updateRule(requestDTO);
            return CultureConverter.toRuleResponseDTO(familyRule);
        }
    }
    @Transactional
    public void deleteRule(Long ruleId, Long userId) {
        FamilyMotto familyRule = familyMottoRepository.findById(ruleId)
                .orElseThrow(() -> new FamilyMottoHandler(ErrorStatus.FAMILYMOTTO_NOT_FOUND));
        // FamilyMotto가 현재 유저가 포함되어 있는지 확인
        FamilyMember familyMember = familyMemberRepository.findByUserIdAndFamily(userId, familyRule.getFamily())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        familyMottoRepository.delete(familyRule);
    }
}
