package com.example.server.converter;

import com.example.server.domian.entity.FamilyMotto;
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
}
