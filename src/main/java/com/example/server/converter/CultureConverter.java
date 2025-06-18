package com.example.server.converter;

import com.example.server.domain.entity.FamilyMotto;
import com.example.server.dto.culture.CultureResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CultureConverter {
    public static CultureResponseDTO.MottoListResponseDTO toMottoListResponseDTO(List<FamilyMotto> familyMottos, boolean hasNext, Long nextCursor)
    {
        List<CultureResponseDTO.MottoResponseDTO> mottoListResponseDTO = familyMottos.stream()
                .map(CultureConverter::toMottoResponseDTO)
                .collect(Collectors.toList());
        return CultureResponseDTO.MottoListResponseDTO.builder()
                .mottos(mottoListResponseDTO)
                .hasNext(hasNext)
                .nextCursor(nextCursor != null ? nextCursor.toString() : null)
                .build();
    }

    public static CultureResponseDTO.MottoResponseDTO toMottoResponseDTO(FamilyMotto familyMotto) {
        return CultureResponseDTO.MottoResponseDTO
                .builder()
                .id(familyMotto.getId())
                .title(familyMotto.getTitle())
                .familyName(familyMotto.getFamily().getName())
                .createdAt(familyMotto.getCreatedAt())
                .build();
    }

    public static CultureResponseDTO.RuleListResponseDTO toRuleListResponseDTO(List<FamilyMotto> responseRules, boolean hasNext, Long nextCursor) {
        List<CultureResponseDTO.RuleResponseDTO> ruleListResponseDTO = responseRules.stream()
                .map(CultureConverter::toRuleResponseDTO)
                .collect(Collectors.toList());
        return CultureResponseDTO.RuleListResponseDTO.builder()
                .rules(ruleListResponseDTO)
                .hasNext(hasNext)
                .nextCursor(nextCursor != null ? nextCursor.toString() : null)
                .build();

    }

    public static CultureResponseDTO.RuleResponseDTO toRuleResponseDTO(FamilyMotto familyMotto) {
        return CultureResponseDTO.RuleResponseDTO.builder()
                .id(familyMotto.getId())
                .title(familyMotto.getTitle())
                .familyName(familyMotto.getFamily().getName())
                .ruleType(familyMotto.getRuleType())
                .createdAt(familyMotto.getCreatedAt())
                .build();
    }
}
