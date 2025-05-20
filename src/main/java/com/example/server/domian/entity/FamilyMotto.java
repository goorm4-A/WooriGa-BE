package com.example.server.domian.entity;

import com.example.server.domian.enums.RuleType;
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
@Table(name = "familyMotto")
public class FamilyMotto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 좌우명 & 규칙

    private String description;

    @Enumerated(EnumType.STRING)
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
}

