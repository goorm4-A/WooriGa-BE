package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyMember> familyMembers = new ArrayList<>();

    @OneToMany(mappedBy="family",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<FamilyDiary> familyDiaries = new ArrayList<>();

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyMotto> familyMottos = new ArrayList<>();

    public Family(String name, Integer inviteCode, String image) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.image = image;
    }

    public void updateFamilyGroup(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
