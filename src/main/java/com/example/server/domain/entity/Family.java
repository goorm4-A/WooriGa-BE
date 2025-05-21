package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer inviteCode;
    private String image;
    private String motto;
    private String mood;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<FamilyMember> familyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<FamilyMotto> familyMottos = new ArrayList<>();
}
