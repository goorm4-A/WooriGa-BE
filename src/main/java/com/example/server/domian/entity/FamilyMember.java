package com.example.server.domian.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "familyMember")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyMotto> familyMottos = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyEvent> familyEvents = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyAnniversary> familyAnniversaries = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyRecipe> familyRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyDiary> familyDiaries = new ArrayList<>();
}
