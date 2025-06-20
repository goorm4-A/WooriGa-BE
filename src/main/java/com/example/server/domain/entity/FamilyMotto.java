package com.example.server.domain.entity;

import com.example.server.domain.enums.RuleType;
import com.example.server.dto.culture.CultureRequestDTO;
import com.example.server.dto.culture.CultureResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMotto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 좌우명 & 규칙

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", length = 20)
    private RuleType ruleType;
/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @ManyToOne
    @JoinColumn(name = "familyMember_id")
    private FamilyMember familyMember;

    public void updateMotto(String title) {
        this.title = title;
    }

    public void updateRule(CultureRequestDTO.RuleRequestDTO ruleRequestDTO) {
        this.ruleType = ruleRequestDTO.getRuleType();
        this.description = ruleRequestDTO.getDescription();
        this.title = ruleRequestDTO.getTitle();
    }
}

