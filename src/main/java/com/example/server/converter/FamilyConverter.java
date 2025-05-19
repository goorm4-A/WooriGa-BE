package com.example.server.converter;

import com.example.server.domian.entity.Family;
import com.example.server.domian.entity.FamilyMember;
import com.example.server.domian.entity.FamilyMotto;
import com.example.server.dto.culture.CultureRequestDTO;

public class FamilyConverter {
    public static FamilyMotto toFamilyMotto(CultureRequestDTO.MottoRequestDTO requestDTO, FamilyMember familyMember, Family family) {
        return new FamilyMotto().builder()
                .title(requestDTO.getMotto())
                .familyMember(familyMember)
                .family(family)
                .build();
    }
}
